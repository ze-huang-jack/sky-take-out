package com.sky.controller.admin;

import com.github.pagehelper.Page;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/admin/dish")
public class DishController {

    @Autowired
    private DishService dishService;

    @GetMapping("/page")
    public Result<PageResult> page(DishPageQueryDTO queryDTO) {
        log.info("菜品分页查询: {}", queryDTO);
        PageResult pageResult = dishService.pageQuery(queryDTO);
        return Result.success(pageResult);
    }

    @PostMapping
    public Result save(@RequestBody DishDTO dishDTO) {
        log.info("新增菜品: {}", dishDTO);
        dishService.save(dishDTO);
        return Result.success();

    }

    @GetMapping("/{id}")
    public Result<Dish> getById(@PathVariable Long id) {
        log.info("根据id查询菜品: {}", id);
        Dish dish = dishService.getById(id);
        return Result.success(dish);
    }

    @GetMapping("/list")
    public Result<List<Dish>> listByCategoryId(Integer categoryId) {
        log.info("根据分类id查询菜品: {}", categoryId);
        List<Dish> dishList = dishService.listByCategoryId(categoryId);
        return Result.success(dishList);
    }

    @PutMapping
    public Result update(@RequestBody DishDTO dishDTO) {
        log.info("修改菜品: {}", dishDTO);
        dishService.update(dishDTO);
        return Result.success();
    }

    @PostMapping("/status/{status}")
    public Result startOrStop(@PathVariable Integer status, Long id) {
        log.info("起售或停售菜品: {} {}", status, id);
        dishService.startOrStop(status, id);
        return Result.success();
    }

    @DeleteMapping
    public Result deleteBatch(@RequestParam List<Long> ids) {
        log.info("批量删除菜品: {}", ids);
        dishService.deleteBatch(ids);
        return Result.success();
    }


}
