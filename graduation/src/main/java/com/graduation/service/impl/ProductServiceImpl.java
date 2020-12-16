package com.graduation.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.graduation.entity.Product;
import com.graduation.mapper.ProductMapper;
import com.graduation.service.ProductService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 叼大
 * @since 2020-12-07
 */
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {
    @Autowired
    private ProductMapper productMapper;

    @Override
    public List<Product> findByCategoryId(String type, Integer categoryId) {
        Map<String, Object> map = new HashMap<>();
        map.put("categorylevel" + type + "_id", categoryId);
        return productMapper.selectByMap(map);
    }

    public List<Product> findAllProduct() {
        return productMapper.selectList(null);
    }

    @Override
    public List<Product> findProductByname(String name) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.like("name",name);
        queryWrapper.or();
        queryWrapper.like("description", name);
        return productMapper.selectList(queryWrapper);
    }
}
