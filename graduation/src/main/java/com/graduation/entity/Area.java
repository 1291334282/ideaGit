package com.graduation.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

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
@ApiModel(value = "Area对象", description = "")
public class Area implements Serializable {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "主键id")
    private String id;
    @ApiModelProperty(value = "产品主键")
    private String name;
    @ApiModelProperty(value = "数量")
    private String code;
}
