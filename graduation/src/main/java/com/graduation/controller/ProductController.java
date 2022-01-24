package com.graduation.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.graduation.config.Cache;
import com.graduation.entity.ResultUtil;
import com.graduation.service.CartService;
import com.graduation.service.ProductCategoryService;
import com.graduation.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    Logger log = LoggerFactory.getLogger(ProductController.class);

    @ApiOperation("功能：根据id查询子商品接口（备注：必须传进参数type和id，其中id为productId，type为第几层，可填one，two）")
    @GetMapping("/findProudListByType")
    public ResultUtil list(@RequestParam(value = "type", required = true) String type, @RequestParam(value = "id", required = true) Integer id) {
        log.info("进入查询子商品接口");
        return ResultUtil.success(productService.findByCategoryId(type, id));
    }

    @ApiOperation("功能：点击商品图片进入详情页（备注：必须传进参数id）")
    @GetMapping("/findProudById")
    public ResultUtil findById(@RequestParam(value = "id", required = true) Integer id) {
        log.info("进入点击商品进入详情页接口");
        return ResultUtil.success(productService.getById(id));
    }

    @ApiOperation("功能：查找全部商品")
    @GetMapping("/findAllProduct")
    @Cache(expire = 5 * 60 * 1000,name = "hot_article")
    public ResultUtil findAllProduct() {
        long startTime = System.currentTimeMillis();
        log.info("进入查找全部商品接口");
        long endTime = System.currentTimeMillis();    //获取结束时间
        System.out.println("程序运行时间：" + (endTime - startTime) + "ms");    //输出程序运行时间
        return ResultUtil.success(productService.findAllProduct());
    }

    @ApiOperation("功能：根据商品名模糊查找商品")
    @GetMapping("/findProductByName")
    public ResultUtil findProductByName(@RequestParam(value = "name", required = true) String name) {
        log.info("进入根据商品名查询商品接口");
        return ResultUtil.success(productService.findProductByname(name));
    }
}

