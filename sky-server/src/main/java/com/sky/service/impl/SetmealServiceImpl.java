package com.sky.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.SetmeaPageQueryDTO;
import com.sky.dto.SetmealDTO;
import com.sky.entity.Dish;
import com.sky.entity.Setmeal;

import com.sky.entity.SetmealDish;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.exception.SetmealEnableFailedException;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealDishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.result.PageResult;
import com.sky.service.SetmealService;
import com.sky.vo.DishItemVO;
import com.sky.vo.SetmealVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SetmealServiceImpl implements SetmealService {
    @Autowired
    private SetmealMapper setmealMapper ;
    @Autowired
    private SetmealDishMapper setmealDishMapper;
    @Autowired
    private SetmealService setmealService;
    @Autowired
    private DishMapper dishMapper;

    @Override
    public PageResult pageQuery(SetmeaPageQueryDTO queryDTO) {
        PageHelper.startPage(queryDTO.getPage(), queryDTO.getPageSize());
        List<SetmealVO> setMealList = setmealMapper.pageQuery(queryDTO);
        PageInfo<SetmealVO> pageInfo = new PageInfo<>(setMealList);
        return new PageResult(pageInfo.getTotal(), pageInfo.getList());
    }

    @Override
    @Transactional
    public void insertWithDishes(SetmealDTO setmealDTO) {
        // 增加一份套餐
        Setmeal setMeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO, setMeal);
        setmealMapper.insert(setMeal);

        Long id = setMeal.getId();
        List<SetmealDish> setmealDishes = setmealDTO.getSetmealDishes();
        if(setmealDishes != null && !setmealDishes.isEmpty()) {
            // 增加套餐对应的n个菜品
            for(SetmealDish sd : setmealDishes) {
                sd.setSetmealId(id);
            }
            setmealDishMapper.insertBatch(setmealDishes);
        }
    }

    @Override
    public SetmealVO getByIdWithDishes(Long id) {
        Setmeal setMeal = setmealMapper.getById(id);
        List<SetmealDish> setMealDishes = setmealDishMapper.getBySetmealId(id);

        SetmealVO setMealVO = new SetmealVO();
        BeanUtils.copyProperties(setMeal, setMealVO);
        setMealVO.setSetmealDishes(setMealDishes);

        return setMealVO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteBatch(List<Long> ids) {
        for(Long id : ids) {
            Setmeal setmeal = setmealMapper.getById(id);
            // 套餐起售 则无法删除
            if(setmeal.getStatus().equals(StatusConstant.ENABLE)) {
                throw new DeletionNotAllowedException(MessageConstant.SETMEAL_ON_SALE);
            }
        }
        for(Long id : ids) {
            // 删除套餐 和 套餐里的菜品
            setmealMapper.deleteById(id);
            setmealDishMapper.deleteBySetmealId(id);
        }
    }

    @Override
    public void update(SetmealDTO setmealDTO) {
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO, setmeal);
        setmealMapper.update(setmeal);

        Long setmealId = setmeal.getId();
        setmealDishMapper.deleteBySetmealId(setmealId);
        List<SetmealDish> setmealDishes = setmealDTO.getSetmealDishes();
        if(setmealDishes != null && !setmealDishes.isEmpty()) {
            for(SetmealDish sd : setmealDishes) {
                sd.setSetmealId(setmealId);
            }
            setmealDishMapper.insertBatch(setmealDishes);
        }
    }

    @Override
    public void startOrStop(Integer status, Long id) {
        if(status.equals(StatusConstant.ENABLE)) {
            // 套餐内包含停售的菜品 无法起售
            List<Dish> dishList = dishMapper.getBySetmealId(id);
            if(dishList != null && !dishList.isEmpty()) {
                for(Dish d : dishList) {
                    if(StatusConstant.DISABLE.equals(d.getStatus())) {
                        throw new SetmealEnableFailedException(MessageConstant.SETMEAL_ENABLE_FAILED);
                    }
                }
            }
        }
        Setmeal setmeal = Setmeal.builder()
                .status(status)
                .id(id)
                .build();
        setmealMapper.update(setmeal);
    }

    @Override
    public List<Setmeal> listByCategoryId(Long categoryId) {
        return setmealMapper.getByCategoryId(categoryId);
    }

    /**
     * 条件查询套餐
     * @param setmeal
     * @return
     */
    @Override
    public List<Setmeal> list(Setmeal setmeal) {
        List<Setmeal> list = setmealMapper.list(setmeal);
        return list;
    }

    /**
     * 根据套餐id查询套餐内菜品选项
     * @param id
     * @return
     */
    @Override
    public List<DishItemVO> getDishItemById(Long id) {
        return setmealDishMapper.getDishItemBySetmealId(id);
    }


}
