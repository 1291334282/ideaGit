package com.graduation.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.graduation.entity.Cart;
import com.graduation.entity.Product;
import com.graduation.enums.CodeEnum;
import com.graduation.exception.MallException;
import com.graduation.mapper.CartMapper;
import com.graduation.mapper.ProductMapper;
import com.graduation.service.CartService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.graduation.vo.CartVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 叼大
 * @since 2020-12-07
 */
@Service
@Slf4j
public class CartServiceImpl extends ServiceImpl<CartMapper, Cart> implements CartService {
    @Autowired
    private CartMapper cartMapper;
    @Autowired
    private ProductMapper productMapper;

    @Override
    public boolean save(Cart entity) {
        //扣库存
        Product product = productMapper.selectById(entity.getProductId());
        Integer stock = product.getStock() - entity.getQuantity();
        if (stock < 0) {
            log.error("【添加购物车】库存不足！stock={}", stock);
            throw new MallException(CodeEnum.STOCK_EMPTY);
        }
        product.setStock(stock);
        productMapper.updateById(product);
        if (cartMapper.insert(entity) == 1) {
            return true;
        }
        return false;
    }

    @Override
    public List<CartVO> findAllCartVOByUserId(Integer id) {
        List<CartVO> cartVOList = new ArrayList<>();
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("user_id", id);
        List<Cart> cartList = cartMapper.selectList(wrapper);
        for (Cart cart : cartList) {
            CartVO cartVO = new CartVO();
            Product product = productMapper.selectById(cart.getProductId());
            BeanUtils.copyProperties(product, cartVO);
            BeanUtils.copyProperties(cart, cartVO);
            cartVOList.add(cartVO);
        }
        return cartVOList;
    }
}
