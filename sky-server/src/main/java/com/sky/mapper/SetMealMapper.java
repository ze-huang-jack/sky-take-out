package com.sky.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SetMealMapper {

    List<Long> getSetMealIdsByDishIds(List<Long> ids);

//    @Select("select count(id) from setmeal where category_id = #{categoryId}")
//    Integer countByCategoryId(Long categoryId);
}
