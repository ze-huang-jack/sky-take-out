package com.sky.mapper;

import com.sky.annotation.AutoFill;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CategoryMapper {

    List<Category> pageQuery(CategoryPageQueryDTO pageQueryDTO);

    @AutoFill(value = OperationType.INSERT)
    void insert(Category category);

    @AutoFill(value = OperationType.UPDATE)
    void update(Category category);

    void delete(Long id);

    List<Category> listByType(Integer type);
}
