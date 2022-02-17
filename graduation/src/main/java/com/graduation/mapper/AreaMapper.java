package com.graduation.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.graduation.entity.Area;
import com.graduation.entity.Cart;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 叼大
 * @since 2020-12-07
 */
public interface AreaMapper extends BaseMapper<Area> {

    int createDataBase();
}
