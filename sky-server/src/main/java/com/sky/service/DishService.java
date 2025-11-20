package com.sky.service;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.vo.DishVO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DishService {

    PageResult pageQuery(DishPageQueryDTO queryDTO);

    void save(DishDTO dishDTO);

    DishVO getById(Long id);

    List<Dish> listByCategoryId(Integer categoryId);

    void update(DishDTO dishDTO);

    void startOrStop(Integer status, Long id);

    void deleteBatch(List<Long> ids);
}
