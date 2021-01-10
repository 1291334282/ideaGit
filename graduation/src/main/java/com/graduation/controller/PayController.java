package com.graduation.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.graduation.config.AlipayConfig;
import com.graduation.entity.Orders;
import com.graduation.entity.ResultUtil;
import com.graduation.entity.User;
import com.graduation.enums.CodeEnum;
import com.graduation.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@RestController
@RequestMapping("/paycheck")
@Api(tags = "支付管理接口")
@CrossOrigin
public class PayController {
    @Autowired
    private OrderService orderService;

    @ApiOperation("功能：支付，备注：需要传入订单的id")
    @GetMapping("/pay")
    public String pay(Integer id, HttpServletRequest request) throws AlipayApiException {
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id, AlipayConfig.merchant_private_key, "json", AlipayConfig.charset, AlipayConfig.alipay_public_key, AlipayConfig.sign_type);

        //设置请求参数
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        alipayRequest.setReturnUrl(AlipayConfig.return_url);
        alipayRequest.setNotifyUrl(AlipayConfig.notify_url);
        Orders order = orderService.getById(id);
        if (!order.getStatus().equals("未支付")) return "该订单已支付";
        //商户订单号，商户网站订单系统中唯一订单号，必填
        String out_trade_no = order.getSerialnumber();
        //付款金额，必填
        String total_amount = order.getCost().toString();
        //订单名称，必填
        String subject = order.getLoginName();
        //商品描述，可空
//        String body = new String(request.getParameter("WIDbody").getBytes("ISO-8859-1"),"UTF-8");

//        alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\","
//                + "\"total_amount\":\""+ total_amount +"\","
//                + "\"subject\":\""+ subject +"\","
//                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
        String timeout_express = "1c";

        String qr_code_timeout_express = "2m";

        alipayRequest.setBizContent("{\"out_trade_no\":\"" + out_trade_no + "\","
                + "\"total_amount\":\"" + total_amount + "\","
                + "\"subject\":\"" + subject + "\","
                + "\"timeout_express\":\"" + timeout_express + "\","
                + "\"qr_code_timeout_express\":\"" + qr_code_timeout_express + "\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
        //若想给BizContent增加其他可选请求参数，以增加自定义超时时间参数timeout_express来举例说明
        //alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\","
        //		+ "\"total_amount\":\""+ total_amount +"\","
        //		+ "\"subject\":\""+ subject +"\","
        //		+ "\"body\":\""+ body +"\","
        //		+ "\"timeout_express\":\"10m\","
        //		+ "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
        //请求参数可查阅【电脑网站支付的API文档-alipay.trade.page.pay-请求参数】章节
        HttpSession session = request.getSession();
        session.setAttribute("id", id);
        //请求
        String result = alipayClient.pageExecute(alipayRequest).getBody();
        return result;
    }

    //    @ApiOperation("功能：支付成功异步做")
//    @GetMapping("/paySuccessDo")
//    public ResultUtil paySuccessDo(){
//        System.out.println("支付成功异步做成功");
//        return ResultUtil.success("支付成功异步做成功","200");
//    }
    @ApiOperation("功能：支付成功跳转")
    @GetMapping("/paySuccessUrl")
    public ResultUtil paySuccessUrl(HttpServletRequest request) throws UnsupportedEncodingException, AlipayApiException {
        HttpSession session = request.getSession(false);
        int id=(Integer) session.getAttribute("id");
        Orders orders = orderService.getById(id);
        orders.setId(id);
        orders.setStatus("未发货");
        orderService.updateById(orders);
        System.out.println("支付成功跳转成功");
        return ResultUtil.success(orders,CodeEnum.PAY_SUCCESS.msg(), CodeEnum.PAY_SUCCESS.val());
    }


}
