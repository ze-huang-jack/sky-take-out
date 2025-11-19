package com.sky.mapper;

import com.sky.annotation.AutoFill;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.enumeration.OperationType;
import com.sky.vo.DishVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DishMapper {
//    @Select("select count(id) from dish where category_id = #{categoryId}")
//    Integer countByCategoryId(Long categoryId);

    List<DishVO> pageQuery(DishPageQueryDTO queryDTO);

//    @AutoFill(value = OperationType.INSERT)
    void save(Dish dish);

    Dish getById(Long id);

    List<Dish> listByCategoryId(Integer categoryId);

    void update(Dish dish);

    void deleteById(Long id);
}
