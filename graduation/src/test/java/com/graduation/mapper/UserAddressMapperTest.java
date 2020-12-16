package com.graduation.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserAddressMapperTest {
    @Autowired
    private UserAddressMapper userAddressMapper;

    @Test
    public void se() {
        System.out.println(userAddressMapper.selectList(null));
    }
}