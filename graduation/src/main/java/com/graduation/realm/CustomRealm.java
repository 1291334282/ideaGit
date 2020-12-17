package com.graduation.realm;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.graduation.entity.User;
import com.graduation.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;

/**
 * 自定义的realm
 */
public class CustomRealm extends AuthorizingRealm {

    public void setName(String name) {
        super.setName("customRealm");
    }

    @Autowired
    private UserService userService;

    /**
     * 授权方法
     * 操作的时候，判断用户是否具有响应的权限
     * 先认证 -- 安全数据
     * 再授权 -- 根据安全数据获取用户具有的所有操作权限
     */
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //1.获取已认证的用户数据
        User user = (User) principalCollection.getPrimaryPrincipal();//得到唯一的安全数据
        //2.根据用户数据获取用户的权限信息（所有角色，所有权限）
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        Set<String> roles = new HashSet<>();//所有角色
//        Set<String> perms = new HashSet<>();//所有权限
//        UserRoleVo userRoleVo=userRoleService.selectByNumber(user.getNumber());
        roles.add(user.getLoginName());
//        perms.add(userRoleVo.getName());
//        info.setStringPermissions(perms);
        info.setRoles(roles);
        return info;
//        return null;
    }


    /**
     * 认证方法
     * 参数：传递的用户名密码
     */
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //1.获取登录的用户名密码（token）
        UsernamePasswordToken upToken = (UsernamePasswordToken) authenticationToken;
        String loginName = upToken.getUsername();
        String password = new String(upToken.getPassword());
        //2.根据用户名查询数据库
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("login_name", loginName);
        User user = userService.getOne(queryWrapper);
        ByteSource salt = ByteSource.Util.bytes(loginName);
        //3.判断用户是否存在或者密码是否一致
        if (user == null) {
            //4.不一致，返回null（抛出异常）
            return null;
        }
        //5.如果一致返回安全数据
        //构造方法：安全数据，密码，realm域名
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, user.getPassword(),salt, this.getName());
        return info;
    }


}
