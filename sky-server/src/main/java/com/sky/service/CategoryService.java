package com.sky.service;

import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.result.PageResult;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryService {

    PageResult pageQuery(CategoryPageQueryDTO pageQueryDTO);

    /**
     * 新增分类
     * @param categoryDTO
     */
    void insert(CategoryDTO categoryDTO);

    /**
     * 修改分类
     * @param categoryDTO
     */
    void update(CategoryDTO categoryDTO);

    void startOrStop(Integer status, Long id);

    void delete(Long id);

    /**
     * 根据type查询分类
     * @param type
     * @return
     */
    List<Category> listByType(Integer type);
}
