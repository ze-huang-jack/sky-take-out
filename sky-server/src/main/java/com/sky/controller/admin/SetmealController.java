package com.sky.controller.admin;

import com.sky.dto.SetmeaPageQueryDTO;
import com.sky.dto.SetmealDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.SetmealService;
import com.sky.vo.SetmealVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/admin/setmeal")
public class SetmealController {

    @Autowired
    private SetmealService setmealService;

    @GetMapping("/page")
    public Result<PageResult> page(SetmeaPageQueryDTO queryDTO) {
        log.info("套餐分页查询: {}", queryDTO);
        PageResult pageResult = setmealService.pageQuery(queryDTO);
        return Result.success(pageResult);
    }

    @PostMapping
    public Result insert(@RequestBody SetmealDTO setmealDTO) {
        log.info("新增套餐: {}", setmealDTO);
        setmealService.insertWithDishes(setmealDTO);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result<SetmealVO> getById(@PathVariable Long id) {
        log.info("根据id查询套餐: {}", id);
        SetmealVO setMealVO = setmealService.getByIdWithDishes(id);
        return Result.success(setMealVO);
    }

    @DeleteMapping
    public Result delete(@RequestParam List<Long> ids) {
        log.info("批量删除套餐: {}", ids);
        setmealService.deleteBatch(ids);
        return Result.success();
    }

    @PutMapping
    public Result update(@RequestBody SetmealDTO setmealDTO) {
        log.info("修改套餐: {}", setmealDTO);
        setmealService.update(setmealDTO);
        return Result.success();
    }

    @PostMapping("/status/{status}")
    public Result startOrStop(@PathVariable Integer status, Long id) {
        log.info("起售或停售套餐: {} {}", status, id);
        setmealService.startOrStop(status, id);
        return Result.success();

    }
}
