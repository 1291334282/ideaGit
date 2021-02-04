package com.graduation.service;

import com.graduation.entity.Orders;
import com.baomidou.mybatisplus.extension.service.IService;
import com.graduation.entity.User;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author 叼大
 * @since 2020-12-07
 */
public interface OrderService extends IService<Orders> {
    public boolean save(Orders orders, User user);

    public List<Orders> selectorder();

    public List<Orders> selectorderbyuserid(Integer userID);

    public Integer selectCount(String status);

    public List<Orders> selectByStatus(String status);

    public boolean deleteOrder(Integer id);
}
