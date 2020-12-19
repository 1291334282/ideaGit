package com.graduation.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.graduation.auth.TokenGenerator;
import com.graduation.entity.SysToken;
import com.graduation.entity.User;
import com.graduation.mapper.SysTokenMapper;
import com.graduation.mapper.UserMapper;
import com.graduation.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private SysTokenMapper sysTokenMapper;

    @Override
    public List<User> selectuser() {
        return userMapper.selectList(null);
    }

    public Map<String, Object> createToken(Integer userId) {
        Map<String, Object> result = new HashMap<>();
        //生成一个token
        String token = TokenGenerator.generateValue();
        //当前时间
        LocalDateTime now = LocalDateTime.now();
        //过期时间
        LocalDateTime expireTime = now.plusHours(12);
        //判断是否生成过token
        SysToken tokenEntity = sysTokenMapper.selectById(userId);
        if (tokenEntity == null) {
            tokenEntity = new SysToken();
            tokenEntity.setUserId(userId);
            //保存token
            tokenEntity.setToken(token);
            tokenEntity.setUpdateTime(now);
            tokenEntity.setExpireTime(expireTime);
            sysTokenMapper.insert(tokenEntity);
        } else {
            //更新token
            tokenEntity.setToken(token);
            tokenEntity.setUpdateTime(now);
            tokenEntity.setExpireTime(expireTime);
            sysTokenMapper.updateById(tokenEntity);
        }
        result.put("token", token);
        result.put("expire", expireTime);
        return result;
    }
    @Override
    public void logout(String token) {


        SysToken byToken = findByToken(token);
        //生成一个token
        token = TokenGenerator.generateValue();
        //修改token
        byToken.setToken(token);
        //使前端获取到的token失效
        sysTokenMapper.updateById(byToken);
    }

    @Override
    public SysToken findByToken(String accessToken) {
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("token",accessToken);
        return sysTokenMapper.selectOne(queryWrapper);
    }
}
