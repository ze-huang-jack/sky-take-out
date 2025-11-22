package com.sky.controller.user;

import com.sky.constant.StatusConstant;
import com.sky.entity.Setmeal;
import com.sky.result.Result;
import com.sky.service.SetmealService;
import com.sky.vo.DishItemVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("userSetmealController")
@Slf4j
@RequestMapping("/user/setmeal")
public class SetmealController {
    @Autowired
    private SetmealService setmealService;

    /**
     * 根据分类id查询套餐
     */
    @GetMapping("/list")
    public Result<List<Setmeal>> list(Long categoryId) {
        log.info("根据分类id查询套餐: {}", categoryId);
        // 条件查询需求分类下起售的套餐
        Setmeal setmeal = new Setmeal();
        setmeal.setCategoryId(categoryId);
        setmeal.setStatus(StatusConstant.ENABLE);
        List<Setmeal> setmeals = setmealService.list(setmeal);
        return Result.success(setmeals);
    }

    /**
     * 根据套餐id查询包含的菜品列表
     */
    @GetMapping("/dish/{id}")
    public Result<List<DishItemVO>> dishItemList(@PathVariable("id") Long id) {
        List<DishItemVO> list = setmealService.getDishItemById(id);
        return Result.success(list);
    }
}
