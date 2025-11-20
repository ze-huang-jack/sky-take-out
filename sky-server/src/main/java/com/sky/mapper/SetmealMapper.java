package com.sky.mapper;

import com.sky.annotation.AutoFill;
import com.sky.dto.SetmeaPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.enumeration.OperationType;
import com.sky.vo.SetmealVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SetmealMapper {

    List<Long> getSetmealIdsByDishIds(List<Long> ids);

    List<SetmealVO> pageQuery(SetmeaPageQueryDTO queryDTO);

    @AutoFill(value = OperationType.INSERT)
    void insert(Setmeal setMeal);

    Setmeal getById(Long id);

    void deleteById(Long id);

    @AutoFill(value = OperationType.UPDATE)
    void update(Setmeal setmeal);

//    @Select("select count(id) from setmeal where category_id = #{categoryId}")
//    Integer countByCategoryId(Long categoryId);
}
