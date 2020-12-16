package com.graduation.service;

import com.graduation.entity.Cart;
import com.baomidou.mybatisplus.extension.service.IService;
import com.graduation.vo.CartVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 叼大
 * @since 2020-12-07
 */
public interface CartService extends IService<Cart> {
    public List<CartVO> findAllCartVOByUserId(Integer id);
}
