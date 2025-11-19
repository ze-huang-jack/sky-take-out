package com.sky.mapper;

import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CategoryMapper {

    List<Category> pageQuery(CategoryPageQueryDTO pageQueryDTO);

    void insert(Category category);

    void update(Category category);

    void delete(Long id);

    List<Category> listByType(Integer type);
}
