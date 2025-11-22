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
    /**
     * 分页查询菜品
     * @param queryDTO
     * @return
     */
    PageResult pageQuery(DishPageQueryDTO queryDTO);

    /**
     * 新增菜品及其口味
     * @param dishDTO
     */
    void save(DishDTO dishDTO);

    /**
     * 根据id查询1份菜品
     * @param id
     * @return
     */
    DishVO getById(Long id);

    /**
     * 根据分类id查询n份菜品
     * @param categoryId
     * @return
     */
    List<Dish> listByCategoryId(Integer categoryId);

    /**
     * 修改菜品和其对应的口味
     * @param dishDTO
     */
    void update(DishDTO dishDTO);

    /**
     * 更新店铺状态
     * @param status
     * @param id
     */
    void startOrStop(Integer status, Long id);

    /**
     * 批量删除菜品
     * @param ids
     */
    void deleteBatch(List<Long> ids);

    /**
     * 通过分类id查询菜品及其口味
     * @param categoryId
     * @return
     */
    public List<DishVO> getByCategoryId(Integer categoryId);

    /**
     * 条件查询菜品和口味
     * @param dish
     * @return
     */
    List<DishVO> listWithFlavors(Dish dish);
}
