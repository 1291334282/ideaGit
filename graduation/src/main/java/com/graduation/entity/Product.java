package com.graduation.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 *
 * </p>
 *
 * @author 叼大
 * @since 2020-12-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "Product对象", description = "")
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "描述")
    private String description;

    @ApiModelProperty(value = "价格")
    private Float price;

    @ApiModelProperty(value = "库存")
    private Integer stock;

    @ApiModelProperty(value = "分类1")
    private Integer categoryleveloneId;

    @ApiModelProperty(value = "分类2")
    private Integer categoryleveltwoId;
//
//    @ApiModelProperty(value = "分类3")
//    private Integer categorylevelthreeId;

    @ApiModelProperty(value = "文件名称")
    private String fileName;


}
