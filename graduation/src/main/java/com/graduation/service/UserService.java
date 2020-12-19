package com.graduation.service;

import com.graduation.entity.SysToken;
import com.graduation.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author 叼大
 * @since 2020-12-07
 */
public interface UserService extends IService<User> {
    public List<User> selectuser();

    Map<String, Object> createToken(Integer userId);

    void logout(String token);

    SysToken findByToken(String accessToken);
}
