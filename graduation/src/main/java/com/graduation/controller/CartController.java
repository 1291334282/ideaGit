package com.graduation.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.graduation.entity.Cart;
import com.graduation.entity.Product;
import com.graduation.entity.ResultUtil;
import com.graduation.entity.User;
import com.graduation.enums.CodeEnum;
import com.graduation.exception.MallException;
import com.graduation.handler.StockHandler;
import com.graduation.service.CartService;
import com.graduation.service.ProductService;
import com.graduation.service.UserAddressService;
import com.graduation.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
@RequestMapping("/cart")
@Api(tags = "订单管理接口")
@CrossOrigin
public class CartController {
    @Autowired
    private CartService cartService;
    @Autowired
    private ProductService productService;
    @Autowired
    private UserService userService;

    @ApiOperation("功能：添加订单到购物车接口(备注：需要传入productId货物表的id，price单价，quantity数量,token)")
    @PostMapping("/addCart")
    public ResultUtil add(@RequestParam(value = "productId", required = true) Integer productId, @RequestParam(value = "price", required = true) Float price, @RequestParam(value = "quantity", required = true) Integer quantity, @RequestHeader("token") String token) {
        User user = userService.getById(userService.findByToken(token).getUserId());
        List<Cart> list = cartService.list(
                new QueryWrapper<Cart>()
                        .eq("product_id", productId)
                        .eq("user_id", user.getId()));
        if (!list.isEmpty())
            return ResultUtil.fail(CodeEnum.PRODUCT_IS_EXISTS.val(), CodeEnum.PRODUCT_IS_EXISTS.msg());
        Cart cart = new Cart();
        cart.setProductId(productId);
        cart.setQuantity(quantity);
        cart.setCost(price * quantity);
        cart.setUserId(user.getId());
        try {
            if (cartService.save(cart)) {
                return ResultUtil.success(null, CodeEnum.ADD_SUCCESS.msg(), CodeEnum.ADD_SUCCESS.val());
            }
        } catch (Exception e) {
            return ResultUtil.fail(CodeEnum.STOCK_EMPTY.val(), CodeEnum.STOCK_EMPTY.msg());
        }
        return ResultUtil.fail(CodeEnum.ADD_FAIL.val(), CodeEnum.ADD_FAIL.msg());
    }

    @ApiOperation("功能：购物车显示接口,备注：（传入token）")
    @GetMapping("/findAllCart")
    public ResultUtil findAllCart(@RequestHeader("token") String token) {
        return ResultUtil.success(cartService.findAllCartVOByUserId(userService.findByToken(token).getUserId()));
    }

    @ApiOperation("功能：删除购物车内容(备注：需要传入购物车内订单对应的id和token)")
    @DeleteMapping("/deleteCartById")
    public ResultUtil deleteById(@RequestParam(value = "id", required = true) Integer id,@RequestHeader("token") String token) {
        cartService.removeById(id);
        return ResultUtil.success(null, CodeEnum.DELETE_SUCCESS.msg(), CodeEnum.DELETE_SUCCESS.val());
    }

    @ApiOperation("功能：动态更新购物车数据，和确认订单时再更新一次(备注：需要传入购物车内订单对应的id，quantity数量，cost单条订单总花费和token)")
    @PutMapping("/updateCartById")
    public ResultUtil updateCart(@RequestParam(value = "id", required = true) Integer id, @RequestParam(value = "quantity", required = true) Integer quantity, @RequestParam(value = "cost", required = true) Float cost,@RequestHeader("token") String token) {
        Cart cart = cartService.getById(id);
        Product product = productService.getById(cart.getProductId());
        try {
            if (quantity != cart.getQuantity()) {
                int i = product.getStock() - (quantity - cart.getQuantity());
                product.setStock(i);
                StockHandler.Stock(i);
                productService.updateById(product);
            }
            cart.setQuantity(quantity);
            cart.setCost(cost);
            if (cartService.updateById(cart)) {
                return ResultUtil.success(null, CodeEnum.UPDATE_SUCCESS.msg(), CodeEnum.UPDATE_SUCCESS.val());
            }
        } catch (Exception e) {
            return ResultUtil.fail(CodeEnum.STOCK_EMPTY.val(), CodeEnum.STOCK_EMPTY.msg());
        }
        return ResultUtil.fail(CodeEnum.UPDATE_FAIL.val(), CodeEnum.UPDATE_FAIL.msg());

    }
}

