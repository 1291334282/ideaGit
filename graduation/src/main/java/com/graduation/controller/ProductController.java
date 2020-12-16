package com.graduation.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.graduation.entity.ResultUtil;
import com.graduation.service.CartService;
import com.graduation.service.ProductCategoryService;
import com.graduation.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 叼大
 * @since 2020-12-07
 */
@RestController
@RequestMapping("/product")
@Api(tags = "商品详情接口")
@CrossOrigin
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductCategoryService productCategoryService;
    @Autowired
    private CartService cartService;

    @ApiOperation("功能：根据id查询子商品接口（备注：必须传进参数type和id，其中id为productId，type为第几层，可填one，two）")
    @GetMapping("/findProudListByType")
    public ResultUtil list(@RequestParam(value = "type", required = true)String type, @RequestParam(value = "id", required = true)Integer id){
        return ResultUtil.success(productService.findByCategoryId(type, id));
    }

    @ApiOperation("功能：点击商品图片进入详情页（备注：必须传进参数id）")
    @GetMapping("/findProudById")
    public ResultUtil findById(@RequestParam(value = "id", required = true)Integer id) {
        return ResultUtil.success(productService.getById(id));
    }

    @ApiOperation("功能：查找全部商品")
    @GetMapping("/findAllProduct")
    public ResultUtil findAllProduct() {
        return ResultUtil.success(productService.findAllProduct());
    }

    @ApiOperation("功能：根据商品名模糊查找商品")
    @GetMapping("/findProductByName")
    public ResultUtil findProductByName(@RequestParam(value = "name", required = true)String name) {
        return ResultUtil.success(productService.findProductByname(name));
    }
}

