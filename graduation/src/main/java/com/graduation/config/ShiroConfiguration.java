package com.graduation.config;



import com.graduation.realm.CustomRealm;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfiguration {
    /**
     * 密码匹配凭证管理器
     *
     * @return
     */
    @Bean(name = "hashedCredentialsMatcher")
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        // 采用MD5方式加密
        hashedCredentialsMatcher.setHashAlgorithmName("MD5");
        // 设置加密次数
        hashedCredentialsMatcher.setHashIterations(1024);
        return hashedCredentialsMatcher;
    }
    //1.创建realm
    @Bean
    public CustomRealm getRealm(@Qualifier("hashedCredentialsMatcher") HashedCredentialsMatcher matcher) {
        CustomRealm customRealm=new CustomRealm();
        customRealm.setCredentialsMatcher(matcher);
        return customRealm;
    }

    //2.创建安全管理器
    @Bean
    public SecurityManager getSecurityManager(CustomRealm realm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(realm);
        //将自定义的会话管理器注册到安全管理器中
//        securityManager.setSessionManager(sessionManager());
        //将自定义的redis缓存管理器注册到安全管理器中
//        securityManager.setCacheManager(cacheManager());
        return securityManager;
    }

    //3.配置shiro的过滤器工厂

    /**
     * 再web程序中，shiro进行权限控制全部是通过一组过滤器集合进行控制
     *
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
        //1.创建过滤器工厂
        ShiroFilterFactoryBean filterFactory = new ShiroFilterFactoryBean();
        //2.设置安全管理器
        filterFactory.setSecurityManager(securityManager);
        //3.通用配置（跳转登录页面，为授权跳转的页面）
        filterFactory.setLoginUrl("/user/nologin");//跳转url地址
        filterFactory.setUnauthorizedUrl("/user/noauth");//未授权的url
//        Map<String, Filter> filters = new LinkedHashMap<>();
//        filters.put("authc", new StatelessAuthcFilter());
//        filterFactory.setFilters(filters);
        //4.设置过滤器集合
        /**
         * 设置所有的过滤器：有顺序map
         *     key = 拦截的url地址
         *     value = 过滤器类型
         *
         */
        Map<String,String> filterMap = new LinkedHashMap<>();
//        filterMap.put("/system/login","anon");
        //Swagger的所有请求的资源和请求的地址都不需要拦截
        filterMap.put("/swagger/**","anon");
        filterMap.put("/v2/api-docs","anon");
        filterMap.put("/swagger-ui.html","anon");
        filterMap.put("/swagger-resources/**","anon");
        filterMap.put("/webjars/**","anon");
        filterMap.put("/favicon.ico","anon");
        filterMap.put("/captcha.jpg","anon");
        filterMap.put("/csrf","anon");
//        filterMap.put("/user/findUserOne","authc");
//        filterMap.put("/user/loginout","authc");
//        filterMap.put("/user/updateUser","authc");
//        filterMap.put("/user/upload","authc");
        filterMap.put("/user/**","anon");
        filterMap.put("/productCategory/**","anon");
        filterMap.put("/product/**","anon");
        //当前请求地址必须认证之后可以访问
//        filterMap.put("/order/**","authc");
//        filterMap.put("/cart/**","authc");
//        filterMap.put("/static/**", "anon");
//        filterMap.put("/userAddress/**","authc");
//        filterMap.put("/admin/**","roles[admin]");
        filterFactory.setFilterChainDefinitionMap(filterMap);
        return filterFactory;
    }


//    @Value("${spring.redis.host}")
//    private String host;
//    @Value("${spring.redis.port}")
//    private int port;

    /**
     * 1.redis的控制器，操作redis
     */
//    public RedisManager redisManager() {
//        RedisManager redisManager = new RedisManager();
//        redisManager.setHost(host);
//        redisManager.setPort(port);
//        return redisManager;
//    }
//
//    /**
//     * 2.sessionDao
//     */
//    public RedisSessionDAO redisSessionDAO() {
//        RedisSessionDAO sessionDAO = new RedisSessionDAO();
//        sessionDAO.setRedisManager(redisManager());
//        return sessionDAO;
//    }
//
//    /**
//     * 3.会话管理器
//     */
//    public DefaultWebSessionManager sessionManager() {
//        CustomSessionManager sessionManager = new CustomSessionManager();
//        sessionManager.setSessionDAO(redisSessionDAO());
//        return sessionManager;
//    }
//
//    /**
//     * 4.缓存管理器
//     */
//    public RedisCacheManager cacheManager() {
//        RedisCacheManager redisCacheManager = new RedisCacheManager();
//        redisCacheManager.setRedisManager(redisManager());
//        return redisCacheManager;
//    }




    //开启对shior注解的支持
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }
}
