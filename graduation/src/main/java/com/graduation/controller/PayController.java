package com.graduation.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.graduation.config.AlipayConfig;
import com.graduation.entity.Orders;
import com.graduation.entity.ResultUtil;
import com.graduation.entity.User;
import com.graduation.enums.CodeEnum;
import com.graduation.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


@RestController
@RequestMapping("/alipay")
@Api(tags = "支付管理接口")
@CrossOrigin
public class PayController {
    @Autowired
    private OrderService orderService;
    Logger log = LoggerFactory.getLogger(PayController.class);
    @ApiOperation("功能：支付，备注：需要传入订单的id")
    @GetMapping("/pay")
    public String pay(Integer id, HttpServletRequest request) throws AlipayApiException {
        log.info("进入支付接口");
        Orders order = orderService.getById(id);

        //获得初始化的AlipayClient
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id, AlipayConfig.merchant_private_key, "json", AlipayConfig.charset, AlipayConfig.alipay_public_key, AlipayConfig.sign_type);

        //设置请求参数
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        alipayRequest.setReturnUrl(AlipayConfig.return_url);
        alipayRequest.setNotifyUrl(AlipayConfig.notify_url);
        if (!order.getStatus().equals("未支付")) return "该订单已支付";
        //商户订单号，商户网站订单系统中唯一订单号，必填
        String out_trade_no = order.getSerialnumber();
        //付款金额，必填
        String total_amount = order.getCost().toString();
        //订单名称，必填
        String subject = order.getLoginName();
        //商品描述，可空
//        String body = "用户订购商品个数：";

        // 该笔订单允许的最晚付款时间，逾期将关闭交易。取值范围：1m～15d。m-分钟，h-小时，d-天，1c-当天（1c-当天的情况下，无论交易何时创建，都在0点关闭）。 该参数数值不接受小数点， 如 1.5h，可转换为 90m。
        String timeout_express = "10m";

        //例子去官方api找
        alipayRequest.setBizContent("{\"out_trade_no\":\"" + out_trade_no + "\","
                + "\"total_amount\":\"" + total_amount + "\","
                + "\"subject\":\"" + subject + "\","
//                + "\"body\":\"" + body + "\","
                + "\"timeout_express\":\"" + timeout_express + "\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");

        //请求
        String result = alipayClient.pageExecute(alipayRequest).getBody();
        System.out.println("==="+result);
        return result;
    }
//
//    @GetMapping("/paySuccessUrl")
//    public ResultUtil paySuccessUrl(HttpServletRequest request) throws UnsupportedEncodingException, AlipayApiException {
//        HttpSession session = request.getSession(false);
//        int id=(Integer) session.getAttribute("id");
//        Orders orders = orderService.getById(id);
//        orders.setId(id);
//        orders.setStatus("未发货");
//        orderService.updateById(orders);
//        System.out.println("支付成功跳转成功");
//        return ResultUtil.success(orders,CodeEnum.PAY_SUCCESS.msg(), CodeEnum.PAY_SUCCESS.val());
//    }
    @ApiOperation("功能：支付成功同步跳转")
    @RequestMapping("alipayReturnNotice")
        public ResultUtil alipayReturnNotice(HttpServletRequest request, HttpServletRequest response, Map map) throws Exception {

        log.info("支付成功, 进入同步通知接口...");

        //获取支付宝GET过来反馈信息
        Map<String, String> params = new HashMap<String, String>();
        Map<String, String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用
            valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }

        boolean signVerified = AlipaySignature.rsaCheckV1(params, AlipayConfig.alipay_public_key, AlipayConfig.charset, AlipayConfig.sign_type); //调用SDK验证签名

        //——请在这里编写您的程序（以下代码仅作参考）——
        if (signVerified) {
            //商户订单号
            String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");

            //支付宝交易号
            String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"), "UTF-8");

            //付款金额
            String total_amount = new String(request.getParameter("total_amount").getBytes("ISO-8859-1"), "UTF-8");

            //页面  展示
            QueryWrapper wrapper = new QueryWrapper();
            wrapper.eq("serialnumber", out_trade_no);
            Orders order = orderService.getOne(wrapper);
            order.setStatus("未发货");
            orderService.updateById(order);
            log.info("********************** 支付成功(支付宝同步通知) **********************");
            log.info("* 订单号: "+out_trade_no);
            log.info("* 支付宝交易号:"+trade_no);
            log.info("* 实付金额:"+total_amount);
            log.info("***************************************************************");

            map.put("out_trade_no", out_trade_no);
            map.put("trade_no", trade_no);
            map.put("total_amount", total_amount);
//            map.put("productName", product.getName());

        } else {
            log.info("支付, 验签失败...");
            return ResultUtil.fail(CodeEnum.PAY_FAIL.val(), CodeEnum.PAY_FAIL.msg());
        }

        //前后分离形式  直接返回页面 记得加上注解@Response  http://login.calidray.com你要返回的网址，再页面初始化时候让前端调用你其他接口，返回信息
        return ResultUtil.success(map,CodeEnum.PAY_SUCCESS.msg(), CodeEnum.PAY_SUCCESS.val());
    }


    /* *
     * 功能：支付宝服务器异步通知页面   对应官方例子 notify_url.jsp     notify_url必须放入公网
     * 日期：2017-03-30
     * 说明：
     * 以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
     * 该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
     *************************页面功能说明*************************  制作业务处理
     * 创建该页面文件时，请留心该页面文件中无任何HTML代码及空格。
     * 该页面不能在本机电脑测试，请到服务器上做测试。请确保外部可以访问该页面。
     * 如果没有收到该页面返回的 success
     * 建议该页面只做支付成功的业务逻辑处理，退款的处理请以调用退款查询接口的结果为准。
     */
    /**
     * @Description: 支付宝异步 通知  制作业务处理
     * @Description TODO
     * @Date 2020-10-29 15:02
     * @Author: StarSea99
     */
    @ApiOperation("功能：支付成功异步处理")
    @RequestMapping(value = "/alipayNotifyNotice")
    public String alipayNotifyNotice(HttpServletRequest request, HttpServletRequest response) throws Exception {

        log.info("支付成功, 进入异步通知接口...");

        //获取支付宝POST过来反馈信息
        Map<String, String> params = new HashMap<String, String>();
        Map<String, String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用
//			valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }

        boolean signVerified = AlipaySignature.rsaCheckV1(params, AlipayConfig.alipay_public_key, AlipayConfig.charset, AlipayConfig.sign_type); //调用SDK验证签名

        //——请在这里编写您的程序（以下代码仅作参考）——

		/* 实际验证过程建议商户务必添加以下校验：
        1、需要验证该通知数据中的out_trade_no是否为商户系统中创建的订单号，
		2、判断total_amount是否确实为该订单的实际金额（即商户订单创建时的金额），
		3、校验通知中的seller_id（或者seller_email) 是否为out_trade_no这笔单据的对应的操作方（有的时候，一个商户可能有多个seller_id/seller_email）
		4、验证app_id是否为该商户本身。
		*/
        if (signVerified) {//验证成功
//            //商户订单号
//            String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");
//
//            //支付宝交易号
//            String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"), "UTF-8");
//
//            //交易状态
//            String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"), "UTF-8");
//
//            //付款金额
//            String total_amount = new String(request.getParameter("total_amount").getBytes("ISO-8859-1"), "UTF-8");
//
//            if (trade_status.equals("TRADE_FINISHED")) {
//                //判断该笔订单是否在商户网站中已经做过处理
//                //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
//                //如果有做过处理，不执行商户的业务程序
//
//                //注意： 尚自习的订单没有退款功能, 这个条件判断是进不来的, 所以此处不必写代码
//                //退款日期超过可退款期限后（如三个月可退款），支付宝系统发送该交易状态通知
//            } else if (trade_status.equals("TRADE_SUCCESS")) {
//                //判断该笔订单是否在商户网站中已经做过处理
//                //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
//                //如果有做过处理，不执行商户的业务程序
//
//                //注意：
//                //付款完成后，支付宝系统发送该交易状态通知
//
//                // 修改叮当状态，改为 支付成功，已付款; 同时新增支付流水
//                ordersService.updateOrderStatus(out_trade_no, trade_no, total_amount);
//
//                //这里不用 查  只是为了 看日志 查的方法应该卸载 同步回调 页面 中
//                Orders order = ordersService.getOrderById(out_trade_no);
//                Product product = productService.getProductById(order.getProductId());
//
//                LOGGER.info("********************** 支付成功(支付宝异步通知)查询 只是为了 看日志  **********************");
//                LOGGER.info("* 订单号: {}", out_trade_no);
//                LOGGER.info("* 支付宝交易号: {}", trade_no);
//                LOGGER.info("* 实付金额: {}", total_amount);
//                LOGGER.info("* 购买产品: {}", product.getName());
//                LOGGER.info("***************************************************************");
//            }
            log.info("支付成功...");
        } else {//验证失败
            log.info("支付, 验签失败...");
        }
        return "success";
    }

}
