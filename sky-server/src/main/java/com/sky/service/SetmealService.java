package com.sky.service;

import com.sky.dto.SetmeaPageQueryDTO;
import com.sky.dto.SetmealDTO;
import com.sky.entity.Setmeal;
import com.sky.result.PageResult;
import com.sky.vo.SetmealVO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SetmealService {

    PageResult pageQuery(SetmeaPageQueryDTO queryDTO);

    /**
     * 新增套餐和对应的菜品
     * @param setmealDTO
     */
    void insertWithDishes(SetmealDTO setmealDTO);

    /**
     * 根据id查询套餐及其对应的菜品
     * @param id
     * @return
     */
    SetmealVO getByIdWithDishes(Long id);

    /**
     * 根据id批量删除
     * @param ids
     */
    void deleteBatch(List<Long> ids);

    /**
     * 修改套餐和套餐里的菜品
     * @param setmealDTO
     */
    void update(SetmealDTO setmealDTO);

    /**
     * 起售或停售套餐
     * @param status
     * @param id
     */
    void startOrStop(Integer status, Long id);

    /**
     * 根据分类id查询套餐内的菜品
     * @param categoryId
     * @return
     */
    List<Setmeal> listByCategoryId(Long categoryId);
}
