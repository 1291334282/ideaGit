package com.graduation.exception;

import com.graduation.entity.ResultUtil;
import com.graduation.enums.CodeEnum;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class MyExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public ResultUtil exceptionHandler(HttpServletRequest req, Exception e) {
        System.out.println("未知异常！原因是:" + e);
        return ResultUtil.fail(CodeEnum.UNKNOW_FAIL.val(), CodeEnum.UNKNOW_FAIL.msg());
    }
}