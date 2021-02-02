package com.graduation.controller;


import com.graduation.entity.ResultUtil;
import com.graduation.service.OrderDetailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
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

    Logger log = LoggerFactory.getLogger(OrderDetailController.class);

    @ApiOperation("功能：查找对应订单的全部商品信息,备注（需要传入token,订单id）")
    @GetMapping("/findOrdersDetail")
    public ResultUtil selectorderDetail(@RequestHeader("token") String token,@RequestParam(value = "id", required = true) Integer id) {
        log.info("进入查找全部订单接口");
        return ResultUtil.success(orderDetailService.selestOrderDetail(id));
    }
}

