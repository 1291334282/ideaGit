package com.graduation.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.graduation.entity.Cart;
import com.graduation.entity.ResultUtil;
import com.graduation.entity.User;
import com.graduation.entity.UserAddress;
import com.graduation.enums.CodeEnum;
import com.graduation.service.UserAddressService;
import com.graduation.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
@Api(tags = "地址管理接口")
@CrossOrigin
@RequestMapping("/userAddress")
public class UserAddressController {
    @Autowired
    private UserAddressService userAddressService;
    @Autowired
    private UserService userService;
    Logger log = LoggerFactory.getLogger(UserAddressController.class);

    @ApiOperation("功能：地址信息显示接口,备注（需要传入token）")
    @GetMapping("/findAllAddress")
    public ResultUtil settlement2(@RequestHeader("token") String token) {
        log.info("进入地址信息显示接口");
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("user_id", userService.findByToken(token).getUserId());
        return ResultUtil.success(userAddressService.list(wrapper));
    }

    @ApiOperation("功能：删除地址(备注：需要传入address的id和token)")
    @DeleteMapping("/deleteAddressById")
    public ResultUtil deleteAddress(@RequestParam(value = "id", required = true) Integer id, @RequestHeader("token") String token) {
        log.info("进入删除地址接口");
        if (userAddressService.removeById(id))
            return ResultUtil.success(null, CodeEnum.DELETE_SUCCESS.msg(), CodeEnum.DELETE_SUCCESS.val());
        return ResultUtil.fail(CodeEnum.DELETE_FAIL.val(), CodeEnum.DELETE_FAIL.msg());
    }

    @ApiOperation("功能：修改一条地址(备注：需要传入address的id和token,userId和更新时间和创建时间不用填，其他选择性填)")
    @PutMapping("/updateAddress")
    public ResultUtil updateAddress(UserAddress userAddress, @RequestHeader("token") String token) {
        log.info("进入修改地址接口");
        User user = userService.getById(userService.findByToken(token).getUserId());
        userAddress.setUserId(user.getId());
        if (userAddress.getDefaultAddress() == 1) {
            UserAddress list = userAddressService.getOne(
                    new QueryWrapper<UserAddress>()
                            .eq("default_address", userAddress.getDefaultAddress())
                            .eq("user_id", userService.findByToken(token).getUserId()));
            if (list!=null) {
                list.setDefaultAddress(0);
                userAddressService.updateById(list);
            }
        }
        if (userAddressService.updateById(userAddress))
            return ResultUtil.success(null, CodeEnum.UPDATE_SUCCESS.msg(), CodeEnum.UPDATE_SUCCESS.val());
        return ResultUtil.fail(CodeEnum.UPDATE_FAIL.val(), CodeEnum.UPDATE_FAIL.msg());
    }

    @ApiOperation("功能：添加一条地址(备注：id和userId和更新时间和创建时间不用填，其他都要填)")
    @PostMapping("/addAddress")
    public ResultUtil addAddress(UserAddress userAddress, @RequestHeader("token") String token) {
        log.info("进入添加地址接口");
        userAddress.setUserId(userService.findByToken(token).getUserId());
        if (userAddress.getDefaultAddress() == 1) {
            UserAddress list = userAddressService.getOne(
                    new QueryWrapper<UserAddress>()
                            .eq("default_address", userAddress.getDefaultAddress())
                            .eq("user_id", userService.findByToken(token).getUserId()));
            if (list!=null) {
                list.setDefaultAddress(0);
                userAddressService.updateById(list);
            }
        }
        if (userAddressService.save(userAddress))
            return ResultUtil.success(null, CodeEnum.ADD_SUCCESS.msg(), CodeEnum.ADD_SUCCESS.val());
        return ResultUtil.fail(CodeEnum.ADD_FAIL.val(), CodeEnum.ADD_FAIL.msg());
    }
}

