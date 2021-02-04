package com.graduation.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.graduation.entity.*;
import com.graduation.mapper.*;
import com.graduation.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 叼大
 * @since 2020-12-07
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Orders> implements OrderService {
    @Autowired
    private UserAddressMapper userAddressMapper;
    @Autowired
    private CartMapper cartMapper;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderDetailMapper orderDetailMapper;
    @Autowired
    private ProductMapper productMapper;

    @Override
    public boolean save(Orders orders, User user) {
        //存储orders
        orderMapper.insert(orders);

        //存储ordersdetail
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("user_id",user.getId());
        List<Cart> cartList = cartMapper.selectList(wrapper);
        for (Cart cart : cartList) {
            OrderDetail orderDetail = new OrderDetail();
            BeanUtils.copyProperties(cart,orderDetail);
            orderDetail.setId(null);
            orderDetail.setOrderId(orders.getId());
            orderDetailMapper.insert(orderDetail);
        }

        //清空购物车
        QueryWrapper wrapper1 = new QueryWrapper();
        wrapper1.eq("user_id",user.getId());
        cartMapper.delete(wrapper1);
        return true;
    }

    @Override
    public List<Orders> selectorder() {
        return orderMapper.selectList(null);
    }

    @Override
    public List<Orders> selectorderbyuserid(Integer userID) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("user_id",userID);
        return orderMapper.selectList(wrapper);
    }

    @Override
    public Integer selectCount(String status) {
        QueryWrapper<Orders> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("status",status);
        return orderMapper.selectCount(queryWrapper);
    }

    @Override
    public List<Orders> selectByStatus(String status) {
        QueryWrapper<Orders> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("status",status);
        return orderMapper.selectList(queryWrapper);
    }

    @Override
    public boolean deleteOrder(Integer id) {
        orderMapper.deleteById(id);
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("order_id",id);
        orderDetailMapper.delete(queryWrapper);
        return true;
    }
}
