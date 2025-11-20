package com.sky.service;

import com.sky.dto.SetmeaPageQueryDTO;
import com.sky.dto.SetmealDTO;
import com.sky.result.PageResult;
import com.sky.vo.SetmealVO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SetmealService {

    PageResult pageQuery(SetmeaPageQueryDTO queryDTO);

    void insertWithDishes(SetmealDTO setmealDTO);

    SetmealVO getByIdWithDishes(Long id);

    void deleteBatch(List<Long> ids);

    void update(SetmealDTO setmealDTO);

    void startOrStop(Integer status, Long id);
}
