package com.graduation.vo;

import com.graduation.entity.Orders;
import com.graduation.entity.Product;
import lombok.Data;

import java.util.List;
@Data
public class OrderDetailVO {
    private Integer id;

    private List<Orders> orders;

    private List<Product> products;

    private Integer quantity;

    private Float cost;
}
