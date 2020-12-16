package com.graduation.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.graduation.entity.ResultUtil;
import com.graduation.entity.User;
import com.graduation.entity.UserAddress;
import com.graduation.enums.CodeEnum;
import com.graduation.service.UserAddressService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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

    @ApiOperation("功能：地址信息显示接口")
    @GetMapping("/findAllAddress")
    public ResultUtil settlement2(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("user_id", user.getId());
        return ResultUtil.success(userAddressService.list(wrapper));
    }

    @ApiOperation("功能：删除地址(备注：需要传入address的id)")
    @PostMapping("/deleteAddressById")
    public ResultUtil deleteAddress(@RequestParam(value = "id", required = true)Integer id) {
        if (userAddressService.removeById(id))
            return ResultUtil.success(null, CodeEnum.DELETE_SUCCESS.msg(), CodeEnum.DELETE_SUCCESS.val());
        return ResultUtil.fail(CodeEnum.DELETE_FAIL.val(), CodeEnum.DELETE_FAIL.msg());
    }

    @ApiOperation("功能：修改一条地址(备注：需要传入address的id,userId和更新时间和创建时间不用填，其他选择性填)")
    @PostMapping("/updateAddress")
    public ResultUtil updateAddress(UserAddress userAddress,HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");
        userAddress.setUserId(user.getId());
        if (userAddressService.updateById(userAddress))
            return ResultUtil.success(null, CodeEnum.UPDATE_SUCCESS.msg(), CodeEnum.UPDATE_SUCCESS.val());
        return ResultUtil.fail(CodeEnum.UPDATE_FAIL.val(), CodeEnum.UPDATE_FAIL.msg());
    }

    @ApiOperation("功能：添加一条地址(备注：id和userId和更新时间和创建时间不用填，其他都要填)")
    @PostMapping("/addAddress")
    public ResultUtil addAddress(UserAddress userAddress,HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");
        userAddress.setUserId(user.getId());
        if (userAddressService.save(userAddress))
            return ResultUtil.success(null, CodeEnum.ADD_SUCCESS.msg(), CodeEnum.ADD_SUCCESS.val());
        return ResultUtil.fail(CodeEnum.ADD_FAIL.val(), CodeEnum.ADD_FAIL.msg());
    }
}

