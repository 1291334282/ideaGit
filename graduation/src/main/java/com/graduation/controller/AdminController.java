package com.graduation.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.graduation.config.AlipayConfig;
import com.graduation.entity.*;
import com.graduation.enums.CodeEnum;
import com.graduation.handler.FileUtil;
import com.graduation.service.OrderService;
import com.graduation.service.ProductService;
import com.graduation.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 叼大
 * @since 2020-12-07
 */
@RestController
@Api(tags = "管理员接口")
@RequestMapping("/admin")
@CrossOrigin
public class AdminController {
    @Autowired
    private UserService userService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private ProductService productService;
    Logger log = LoggerFactory.getLogger(AdminController.class);

    @RequiresRoles({"admin"})
    @ApiOperation("功能：退款，备注：需要传入订单的id和token")
    @GetMapping("/payback")
    public ResultUtil payback(@RequestParam(value = "id", required = true) Integer id, @RequestHeader("token") String token) throws AlipayApiException {
        log.info("进入退款接口");
        if (!userService.findByToken(token).getUserId().equals(1))
            return ResultUtil.fail(CodeEnum.NO_AUTH.val(), CodeEnum.NO_AUTH.msg());
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id, AlipayConfig.merchant_private_key, "json", AlipayConfig.charset, AlipayConfig.alipay_public_key, AlipayConfig.sign_type);

        //设置请求参数
        AlipayTradeRefundRequest alipayRequest = new AlipayTradeRefundRequest();
        Orders order = orderService.getById(id);
        if (!order.getStatus().equals("申请退款"))
            return ResultUtil.fail(CodeEnum.PAYBACK_FAIL.val(), CodeEnum.PAYBACK_FAIL.msg());
        //商户订单号，商户网站订单系统中唯一订单号
        String out_trade_no = order.getSerialnumber();
        //支付宝交易号
//        String trade_no = new String(request.getParameter("WIDTRtrade_no").getBytes("ISO-8859-1"),"UTF-8");
        //请二选一设置
        //需要退款的金额，该金额不能大于订单金额，必填
        String refund_amount = order.getCost().toString();
        //退款的原因说明
        String refund_reason = order.getReason();
        //标识一次退款请求，同一笔交易多次退款需要保证唯一，如需部分退款，则此参数必传
        String out_request_no = order.getSerialnumber();
        order.setStatus("已退货");
        orderService.updateById(order);
        alipayRequest.setBizContent("{\"out_trade_no\":\"" + out_trade_no + "\","
                + "\"refund_amount\":\"" + refund_amount + "\","
                + "\"refund_reason\":\"" + refund_reason + "\","
                + "\"out_request_no\":\"" + out_request_no + "\"}");

        //请求
        String result = alipayClient.execute(alipayRequest).getBody();

        return ResultUtil.success(result, CodeEnum.PAYBACK_SUCCESS.msg(), CodeEnum.PAYBACK_SUCCESS.val());
    }

    @RequiresRoles({"admin"})
    @ApiOperation("功能：删除个人信息(备注：管理员使用，需要传入user的id)")
    @DeleteMapping("/deleteUserById")
    public ResultUtil deleteuser(@RequestParam(value = "id", required = true) Integer id, @RequestHeader("token") String token) {
        log.info("进入删除个人信息接口");
        if (!userService.findByToken(token).getUserId().equals(1))
            return ResultUtil.fail(CodeEnum.NO_AUTH.val(), CodeEnum.NO_AUTH.msg());
        if (userService.removeById(id))
            return ResultUtil.success(null, CodeEnum.DELETE_SUCCESS.msg(), CodeEnum.DELETE_SUCCESS.val());
        return ResultUtil.fail(CodeEnum.DELETE_FAIL.val(), CodeEnum.DELETE_FAIL.msg());
    }

    @RequiresRoles({"admin"})
    @ApiOperation("功能：查找全部个人信息(备注：管理员使用)")
    @GetMapping("/findAllUser")
    public ResultUtil selectuser(@RequestHeader("token") String token) {
        log.info("进入查找全部个人信息接口");
        if (!userService.findByToken(token).getUserId().equals(1))
            return ResultUtil.fail(CodeEnum.NO_AUTH.val(), CodeEnum.NO_AUTH.msg());
        return ResultUtil.success(userService.selectuser());
    }

    @RequiresRoles({"admin"})
    @ApiOperation("功能：修改订单状态,把未发货改成已发货(备注：管理员使用，需要传入orders的id")
    @PutMapping("/updateOrdersStatus")
    public ResultUtil updateAddress(@RequestParam(value = "id", required = true) Integer id, @RequestHeader("token") String token) {
        log.info("进入修改订单状态接口");
        if (!userService.findByToken(token).getUserId().equals(1))
            return ResultUtil.fail(CodeEnum.NO_AUTH.val(), CodeEnum.NO_AUTH.msg());
        Orders orders = new Orders();
        orders.setId(id);
        orders.setStatus("已发货");
        if (orderService.updateById(orders))
            return ResultUtil.success(null, CodeEnum.UPDATE_SUCCESS.msg(), CodeEnum.UPDATE_SUCCESS.val());
        return ResultUtil.fail(CodeEnum.UPDATE_FAIL.val(), CodeEnum.UPDATE_FAIL.msg());
    }

    @RequiresRoles({"admin"})
    @ApiOperation("功能：查找全部订单(备注：管理员使用)")
    @GetMapping("/findAllOrders")
    public ResultUtil selectorder(@RequestHeader("token") String token) {
        log.info("进入查找全部订单接口");
        if (!userService.findByToken(token).getUserId().equals(1))
            return ResultUtil.fail(CodeEnum.NO_AUTH.val(), CodeEnum.NO_AUTH.msg());
        return ResultUtil.success(orderService.selectorder());
    }

    @ApiOperation("功能：上架商品(备注：管理员使用,name,price,stcok,categoryleveloneId,categoryleveltwoId为必填项,图片必须上传，fileName不用填)")
    @PostMapping("/addProduct")
    public ResultUtil addProduct(Product product, @RequestParam("file") MultipartFile file, @RequestHeader("token") String token) {
        log.info("进入上架商品接口");
        if (!userService.findByToken(token).getUserId().equals(1))
            return ResultUtil.fail(CodeEnum.NO_AUTH.val(), CodeEnum.NO_AUTH.msg());
        String fileName = file.getOriginalFilename();//获取文件名
        String filepath = FileUtil.getUploadPath();
        if (!file.isEmpty()) {
            try (BufferedOutputStream out = new BufferedOutputStream(
                    new FileOutputStream(new File(filepath + File.separator + fileName)))) {
                out.write(file.getBytes());
                out.flush();
                product.setFileName(fileName);
                productService.save(product);
                return ResultUtil.success(fileName, CodeEnum.ADD_SUCCESS.msg(), CodeEnum.ADD_SUCCESS.val());
            } catch (FileNotFoundException e) {
                return ResultUtil.fail(CodeEnum.ADD_FAIL.val(), CodeEnum.ADD_FAIL.msg());
            } catch (IOException e) {
                return ResultUtil.fail(CodeEnum.ADD_FAIL.val(), CodeEnum.ADD_FAIL.msg());
            }
        } else {
            return ResultUtil.fail(CodeEnum.FILE_EMPTY.val(), CodeEnum.FILE_EMPTY.msg());
        }
    }

    @RequiresRoles({"admin"})
    @ApiOperation("功能：下架商品(备注：管理员使用，需要传入product的id)")
    @DeleteMapping("/deleteProductById")
    public ResultUtil deleteProduct(@RequestParam(value = "id", required = true) Integer id, @RequestHeader("token") String token) {
        log.info("进入下架商品接口");
        if (!userService.findByToken(token).getUserId().equals(1))
            return ResultUtil.fail(CodeEnum.NO_AUTH.val(), CodeEnum.NO_AUTH.msg());
        if (productService.removeById(id))
            return ResultUtil.success(null, CodeEnum.DELETE_SUCCESS.msg(), CodeEnum.DELETE_SUCCESS.val());
        return ResultUtil.fail(CodeEnum.DELETE_FAIL.val(), CodeEnum.DELETE_FAIL.msg());
    }

    @RequiresRoles({"admin"})
    @ApiOperation("功能：修改商品信息(备注：管理员使用，需要传入product的id,选择性传入其他信息")
    @PutMapping("/updateProduct")
    public ResultUtil updateAddress(Product product, MultipartFile file, @RequestHeader("token") String token) {
        log.info("进入修改商品信息接口");
        if (!userService.findByToken(token).getUserId().equals(1))
            return ResultUtil.fail(CodeEnum.NO_AUTH.val(), CodeEnum.NO_AUTH.msg());
        if (file == null) {
            productService.updateById(product);
            return ResultUtil.success(null, CodeEnum.UPDATE_SUCCESS.msg(), CodeEnum.UPDATE_SUCCESS.val());
        }
        if (!file.isEmpty()) {
            String filepath = FileUtil.getUploadPath();
            String fileName = file.getOriginalFilename();//获取文件名
            try (BufferedOutputStream out = new BufferedOutputStream(
                    new FileOutputStream(new File(filepath + File.separator + fileName)))) {
                out.write(file.getBytes());
                out.flush();
                product.setFileName(fileName);
                productService.updateById(product);
                return ResultUtil.success(null, CodeEnum.UPDATE_SUCCESS.msg(), CodeEnum.UPDATE_SUCCESS.val());
            } catch (FileNotFoundException e) {
                return ResultUtil.fail(CodeEnum.UPDATE_FAIL.val(), CodeEnum.UPDATE_FAIL.msg());
            } catch (IOException e) {
                return ResultUtil.fail(CodeEnum.UPDATE_FAIL.val(), CodeEnum.UPDATE_FAIL.msg());
            }
        } else {
            return ResultUtil.fail(CodeEnum.FILE_EMPTY.val(), CodeEnum.FILE_EMPTY.msg());
        }
    }
}
