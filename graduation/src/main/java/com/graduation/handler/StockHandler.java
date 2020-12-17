package com.graduation.handler;

import com.graduation.enums.CodeEnum;
import com.graduation.exception.MallException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StockHandler {
    public static void Stock(int stock){
        if (stock < 0) {
            log.error("【添加购物车】库存不足！stock={}", stock);
            throw new MallException(CodeEnum.STOCK_EMPTY);
        }
    }
}
