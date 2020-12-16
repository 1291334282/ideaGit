package com.graduation.service;

import com.graduation.entity.Product;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author 叼大
 * @since 2020-12-07
 */
public interface ProductService extends IService<Product> {
    public List<Product> findByCategoryId(String type, Integer categoryId);

    public List<Product> findAllProduct();

    public List<Product> findProductByname(String name);
}
