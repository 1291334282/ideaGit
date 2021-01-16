package com.graduation.controller;


import com.graduation.entity.ResultUtil;
import com.graduation.service.ProductCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 叼大
 * @since 2020-12-07
 */
@RestController
@Api(tags = "返回两层商品集合接口")
@RequestMapping("/productCategory")
@CrossOrigin
public class ProductCategoryController {
    @Autowired
    private ProductCategoryService productCategoryService;
    Logger log = LoggerFactory.getLogger(ProductCategoryController.class);

    @ApiOperation("功能：返回商品接口")
    @GetMapping("/list")
    public ResultUtil list() {
        log.info("进入返回商品列表接口");
        return ResultUtil.success(productCategoryService.getAllProductCategoryVO());
    }
}

