package com.graduation.service;

import com.graduation.entity.OrderDetail;
import com.baomidou.mybatisplus.extension.service.IService;
import com.graduation.entity.Product;
import com.graduation.vo.OrderByNameVO;
import com.graduation.vo.OrderDetailVO;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author 叼大
 * @since 2020-12-07
 */
public interface OrderDetailService extends IService<OrderDetail> {
    public List<OrderDetailVO> selestOrderDetail(Integer id);

    public List<OrderByNameVO> selestOrderByName(String name);
}
