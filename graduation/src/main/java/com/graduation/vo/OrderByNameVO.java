package com.graduation.vo;

import com.graduation.entity.Product;
import lombok.Data;


@Data
public class OrderByNameVO {
    private Integer id;

    private Integer orderId;

    private Product product;

    private Integer quantity;

    private Float cost;
}
