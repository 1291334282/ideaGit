package com.graduation.service;

import com.graduation.entity.ProductCategory;
import com.baomidou.mybatisplus.extension.service.IService;
import com.graduation.vo.ProductCategoryVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 叼大
 * @since 2020-12-07
 */
public interface ProductCategoryService extends IService<ProductCategory> {
    public List<ProductCategoryVO> getAllProductCategoryVO();
}
