package com.graduation.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * token实体类
 * @Author 大誌
 * @Date 2019/3/31 10:56
 * @Version 1.0
 */
@Data
public class SysToken implements Serializable {

    /**
     * 用户ID
     */
    @TableId(value = "user_id", type = IdType.INPUT)
    private Integer userId;

    /**
     * token
     */
    private String token;

    /**
     * 过期时间
     */
    private LocalDateTime expireTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}
