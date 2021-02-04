package com.graduation.controller;


import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.graduation.config.AlipayConfig;
import com.graduation.entity.Orders;
import com.graduation.entity.ResultUtil;
import com.graduation.entity.User;
import com.graduation.enums.CodeEnum;
import com.graduation.service.CartService;
import com.graduation.service.OrderService;
import com.graduation.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Random;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 叼大
 * @since 2020-12-07
 */
@RestController
@RequestMapping("/order")
@Api(tags = "订单提交接口")
@CrossOrigin
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private CartService cartService;
    @Autowired
    private UserService userService;
    Logger log = LoggerFactory.getLogger(OrderController.class);

    @ApiOperation("功能：点击订单确认后添加到orders里(备注：需要传入总金额cost,用户地址userAddress,token)")
    @PostMapping("/addOrder")
    public ResultUtil settlement3(Orders orders, @RequestHeader("token") String token) {
        log.info("进入添加订单接口");
        User user = userService.getById(userService.findByToken(token).getUserId());
        orders.setUserId(user.getId());
        orders.setLoginName(user.getLoginName());
        orders.setStatus("未支付");
        String seriaNumber = null;
        try {
            StringBuffer result = new StringBuffer();
            for (int i = 0; i < 32; i++) {
                result.append(Integer.toHexString(new Random().nextInt(16)));
            }
            seriaNumber = result.toString().toUpperCase();
        } catch (Exception e) {
            e.printStackTrace();
        }
        orders.setSerialnumber(seriaNumber);
        orderService.save(orders, user);
        return ResultUtil.success(orders, CodeEnum.ADD_SUCCESS.msg(), CodeEnum.ADD_SUCCESS.val());
    }

    @ApiOperation("功能：查找对应登录人的全部订单,备注（需要传入token）")
    @GetMapping("/findOrders")
    public ResultUtil selectorder(@RequestHeader("token") String token) {
        log.info("进入查找全部订单接口");
        return ResultUtil.success(orderService.selectorderbyuserid(userService.findByToken(token).getUserId()));
    }

    @ApiOperation("功能：修改状态为退款待审核，备注：需要传入订单的id和token和退款原因")
    @PutMapping("/payback")
    public ResultUtil payback(@RequestParam(value = "id", required = true) Integer id, @RequestHeader("token") String token, @RequestParam(value = "reason", required = true) String reason) {
        log.info("进入退款接口");
        Orders orders = new Orders();
        orders.setId(id);
        orders.setStatus("申请退款");
        orders.setReason(reason);
        if (orderService.updateById(orders))
            return ResultUtil.success(null, "退款待审核", "200");
        return ResultUtil.fail(CodeEnum.UPDATE_FAIL.val(), CodeEnum.UPDATE_FAIL.msg());

    }

    @ApiOperation("功能：删除订单，备注：需要传入订单的id和token")
    @DeleteMapping("/deleteOrder")
    public ResultUtil deleteOrder(@RequestParam(value = "id", required = true) Integer id, @RequestHeader("token") String token) {
        log.info("进入删除订单接口");
        orderService.deleteOrder(id);
        return ResultUtil.success(null, "删除成功", "200");
    }
}

