package com.graduation.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.graduation.entity.OrderDetail;
import com.graduation.entity.Product;
import com.graduation.mapper.OrderDetailMapper;
import com.graduation.mapper.ProductMapper;
import com.graduation.service.OrderDetailService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.graduation.vo.OrderDetailVO;
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
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail> implements OrderDetailService {

    @Autowired
    private OrderDetailMapper orderDetailMapper;

    @Autowired
    private ProductMapper productMapper;

    @Override
    public List<OrderDetailVO> selestOrderDetail(Integer id) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("order_id", id);
        List<OrderDetail> orderDetail = orderDetailMapper.selectList(wrapper);
        OrderDetailVO orderDetailVOS = new OrderDetailVO();
        List<OrderDetailVO> list = new ArrayList<>();
        for (OrderDetail orderDetail1 : orderDetail) {
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq("id", orderDetail1.getProductId());
            BeanUtils.copyProperties(orderDetail1, orderDetailVOS);
            orderDetailVOS.setProducts(productMapper.selectList(queryWrapper));
            list.add(orderDetailVOS);
        }
        return list;
    }
}
