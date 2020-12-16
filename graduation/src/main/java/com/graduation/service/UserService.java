package com.graduation.service;

import com.graduation.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 叼大
 * @since 2020-12-07
 */
public interface UserService extends IService<User> {
    public List<User> selectuser();
}
