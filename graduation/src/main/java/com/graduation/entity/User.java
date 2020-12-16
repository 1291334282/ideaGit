package com.graduation.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;

import com.graduation.enums.GenderEnum;
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
@ApiModel(value = "User对象", description = "")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键,不用输入")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "登录名,要输入")
    private String loginName;

    @ApiModelProperty(value = "用户名,要输入")
    private String userName;

    @ApiModelProperty(value = "密码,要输入")
    private String password;

    @ApiModelProperty(value = "性别(1:男 0：女)")
    private GenderEnum gender;

    @ApiModelProperty(value = "身份证号,要输入")
    private String identityCode;

    @ApiModelProperty(value = "邮箱,要输入")
    private String email;

    @ApiModelProperty(value = "手机,要输入")
    private String mobile;
    @ApiModelProperty(value = "头像,不用输入")
    private String fileName;
    @ApiModelProperty(value = "创建时间,不用输入")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @ApiModelProperty(value = "更新时间,不用输入")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;


}
