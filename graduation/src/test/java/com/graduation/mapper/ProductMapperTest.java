package com.graduation.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class UserMapperTest {

    @Autowired
    private UserMapper productMapper;

    @Test
    void Test() {
        productMapper.selectList(null).forEach(System.out::println);
    }
}