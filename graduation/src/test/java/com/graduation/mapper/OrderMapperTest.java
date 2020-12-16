package com.graduation.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class OrderMapperTest {
    @Value("${prop.upload-folder}")
    private String UPLOAD_FOLDER;
    @Test
    public void setUPLOAD_FOLDER(){
        System.out.println(UPLOAD_FOLDER);
    }
}