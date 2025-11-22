package com.sky.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.DishFlavorMapper;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.result.PageResult;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
public class DishServiceImpl implements DishService {

    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private DishFlavorMapper dishFlavorMapper;
    @Autowired
    private SetmealMapper setmealMapper;

    @Override
    public PageResult pageQuery(DishPageQueryDTO queryDTO) {
        PageHelper.startPage(queryDTO.getPage(), queryDTO.getPageSize());
        List<DishVO> dishList = dishMapper.pageQuery(queryDTO);
        PageInfo<DishVO> pageInfo = new PageInfo<>(dishList);
        return new PageResult(pageInfo.getTotal(), pageInfo.getList());
    }

    /**
     * 新增菜品及其口味
     *
     * @param dishDTO
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(DishDTO dishDTO) {
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO, dish);
        dishMapper.save(dish);

        Long id = dish.getId();
        List<DishFlavor> dishFlavor = dishDTO.getFlavors();
        if (dishFlavor != null && !dishFlavor.isEmpty()) {
            for (DishFlavor df : dishFlavor) {
                df.setDishId(id);
            }
            dishFlavorMapper.insertBatch(dishFlavor);
        }
    }

    /**
     * 根据id查询菜品及其口味
     *
     * @param id
     * @return
     */
    @Override
    public DishVO getById(Long id) {
        Dish dish = dishMapper.getById(id);
        List<DishFlavor> dishFlavors = dishFlavorMapper.getByDishId(id);
        DishVO dishVO = new DishVO();
        BeanUtils.copyProperties(dish, dishVO);
        dishVO.setFlavors(dishFlavors);
        return dishVO;
    }

    /**
     * 根据分类id查询菜品
     *
     * @param categoryId
     * @return
     */
    @Override
    public List<Dish> listByCategoryId(Integer categoryId) {
        return dishMapper.listByCategoryId(categoryId);
    }

    /**
     * 修改菜品及其口味
     *
     * @param dishDTO
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(DishDTO dishDTO) {
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO, dish);
        dishMapper.update(dish);

        Long id = dishDTO.getId();
        dishFlavorMapper.deleteByDishId(id);
        List<DishFlavor> dishFlavor = dishDTO.getFlavors();
        if (dishFlavor != null && !dishFlavor.isEmpty()) {
            for (DishFlavor df : dishFlavor) {
                df.setDishId(id);
            }
            dishFlavorMapper.insertBatch(dishFlavor);
        }
    }

    /**
     * 起售或停售菜品
     *
     * @param status
     * @param id
     */
    @Override
    public void startOrStop(Integer status, Long id) {
        Dish dish = Dish.builder().id(id).status(status).build();
        dishMapper.update(dish);
    }

    /**
     * 批量删除菜品及其口味
     *
     * @param ids
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteBatch(List<Long> ids) {
        for (Long id : ids) {
            Dish dish = dishMapper.getById(id);
            // 菜品起售 则无法删除
            if (dish.getStatus().equals(StatusConstant.ENABLE)) {
                throw new DeletionNotAllowedException(MessageConstant.DISH_ON_SALE);
            }
        }

        List<Long> setMealIds = setmealMapper.getSetmealIdsByDishIds(ids);
        // 有关联的套餐 无法删除
        if (setMealIds != null && !setMealIds.isEmpty()) {
            throw new DeletionNotAllowedException(MessageConstant.DISH_BE_RELATED_BY_SETMEAL);
        }

        for (Long id : ids) {
            dishMapper.deleteById(id);
            dishFlavorMapper.deleteByDishId(id);
        }
    }

    /**
     * 根据分类id查询菜品及其口味
     *
     * @param categoryId
     * @return
     */
    @Override
    public List<DishVO> getByCategoryId(Integer categoryId) {
        List<Dish> dishes = dishMapper.listByCategoryId(categoryId);
        if (dishes == null || dishes.isEmpty()) {
            return Collections.emptyList();
        }
        List<DishVO> dishVOs = new ArrayList<>();
        for(Dish dish : dishes) {
            DishVO dishVO = new DishVO();
            BeanUtils.copyProperties(dish, dishVO);
            List<DishFlavor> dishFlavors = dishFlavorMapper.getByDishId(dish.getId());
            dishVO.setFlavors(dishFlavors);
            dishVOs.add(dishVO);
        }
        return dishVOs;


    }


}
