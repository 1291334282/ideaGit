package com.graduation.controller;


import com.graduation.entity.Orders;
import com.graduation.entity.ResultUtil;
import com.graduation.service.OrderDetailService;
import com.graduation.service.OrderService;
import com.graduation.service.UserService;
import com.graduation.vo.OrderDetailVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 叼大
 * @since 2020-12-07
 */
@RestController
@RequestMapping("/orderDetail")
@Api(tags = "订单显示详情接口")
@CrossOrigin
public class OrderDetailController {
    @Autowired
    private OrderDetailService orderDetailService;

    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;

    Logger log = LoggerFactory.getLogger(OrderDetailController.class);

    @ApiOperation("功能：查找对应登录人的全部订单详情,备注（需要传入token）")
    @GetMapping("/findOrdersAll")
    public ResultUtil selectorderAll(@RequestHeader("token") String token) {
        log.info("进入查找全部订单详情接口");
        List<Orders> orders = orderService.selectorderbyuserid(userService.findByToken(token).getUserId());
        List<List<OrderDetailVO>> list = new ArrayList<>();
        for (Orders order : orders) {
            list.add(orderDetailService.selestOrderDetail(order.getId()));
        }
        return ResultUtil.success(list);
    }

    @ApiOperation("功能：查找订单id对应的订单的详细信息,备注（需要传入token，订单的id号，不是订单号哦）")
    @GetMapping("/findOrdersById")
    public ResultUtil selectorderById(@RequestHeader("token") String token,@RequestParam(value = "id", required = true) Integer id) {
        log.info("进入查找订单id对应的订单的详细信息的接口");
        return ResultUtil.success(orderDetailService.selestOrderDetail(id));
    }

    @ApiOperation("功能：根据商品名称或描述模糊查询订单的详细信息,备注（需要传入token，name）")
    @GetMapping("/findOrdersByNumber")
    public ResultUtil selectorderByNumber(@RequestHeader("token") String token,@RequestParam(value = "name", required = true) String name) {
        log.info("进入根据商品名称或描述模糊查询订单的接口");
        return ResultUtil.success(orderDetailService.selestOrderByName(name));
    }
}

