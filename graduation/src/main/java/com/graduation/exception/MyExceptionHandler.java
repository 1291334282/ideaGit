package com.graduation.exception;

import com.graduation.controller.UserController;
import com.graduation.entity.ResultUtil;
import com.graduation.enums.CodeEnum;
import org.apache.shiro.authz.AuthorizationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class MyExceptionHandler {
    Logger log = LoggerFactory.getLogger(MyExceptionHandler.class);
    @ExceptionHandler(value = Exception.class)
    public ResultUtil exceptionHandler(HttpServletRequest req, Exception e) {
        log.error("未知异常！原因是:" + e);
        return ResultUtil.fail(CodeEnum.UNKNOW_FAIL.val(), CodeEnum.UNKNOW_FAIL.msg());
    }
    @ExceptionHandler(value = AuthorizationException.class)
    public ResultUtil handleException(AuthorizationException e) {
        //e.printStackTrace();
        //获取错误中中括号的内容
        String message = e.getMessage();
        String msg=message.substring(message.indexOf("[")+1,message.indexOf("]"));
        //判断是角色错误还是权限错误
        if (message.contains("role")) {
            return ResultUtil.fail("500","对不起，您没有" + msg + "角色");
        } else if (message.contains("permission")) {

            return ResultUtil.fail("500","对不起，您没有" + msg + "权限");
        } else {

            return ResultUtil.fail("500","对不起，您的权限有误");
        }

    }
}