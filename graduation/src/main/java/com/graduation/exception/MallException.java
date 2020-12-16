package com.graduation.exception;


import com.graduation.enums.CodeEnum;

/**
 * unchecked 不用去处理，交给JVM去处理，继承 RuntimeException
 * checked，需要自己处理，继承 Exception
 */
public class MallException extends RuntimeException {
    public MallException(String error) {
        super(error);
    }

    public MallException(CodeEnum resultEnum) {
        super(resultEnum.STOCK_EMPTY.msg());
    }
}
