package com.sky.mapper;

import com.sky.annotation.AutoFill;
import com.sky.entity.DishFlavor;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper
public interface DishFlavorMapper {

    /**
     * 批量添加菜品口味
     * @param flavors
     */
    void insertBatch(List<DishFlavor> flavors);

    /**
     * 删除要求菜品对应的口味
     * @param id
     */
    void deleteByDishId(Long id);

    /**
     * 查询菜品对应的口味
     * @param id
     * @return
     */
    List<DishFlavor> getByDishId(Long id);
}
