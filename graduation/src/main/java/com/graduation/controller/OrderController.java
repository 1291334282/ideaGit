package com.graduation.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.graduation.entity.Orders;
import com.graduation.entity.ResultUtil;
import com.graduation.entity.User;
import com.graduation.enums.CodeEnum;
import com.graduation.service.CartService;
import com.graduation.service.OrderService;
import com.graduation.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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

    @ApiOperation("功能：点击订单确认后添加到orders里(备注：需要传入总金额cost,用户地址userAddress,token)")
    @PostMapping("/addOrder")
    public ResultUtil settlement3(Orders orders, @RequestHeader("token") String token) {
        User user = userService.getById(userService.findByToken(token).getUserId());
        orders.setUserId(user.getId());
        orders.setLoginName(user.getLoginName());
        orders.setStatus("未发货");
        String seriaNumber = null;
        try {
            StringBuffer result = new StringBuffer();
            for(int i=0;i<32;i++) {
                result.append(Integer.toHexString(new Random().nextInt(16)));
            }
            seriaNumber =  result.toString().toUpperCase();
        } catch (Exception e) {
            e.printStackTrace();
        }
        orders.setSerialnumber(seriaNumber);
        orderService.save(orders, user);
        return ResultUtil.success(orders,CodeEnum.ADD_SUCCESS.msg(),CodeEnum.ADD_SUCCESS.val());
    }

    @ApiOperation("功能：查找对应登录人的全部订单,备注（需要传入token）")
    @GetMapping("/findOrders")
    public ResultUtil selectorder(@RequestHeader("token") String token) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("user_id",userService.findByToken(token).getUserId());
        return ResultUtil.success(orderService.getMap(wrapper));
    }
}

