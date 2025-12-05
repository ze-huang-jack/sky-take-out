package com.sky.controller.admin;

import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/admin/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/page")
    public Result<PageResult> page(CategoryPageQueryDTO pageQueryDTO) {
        log.info("分页查询分类: {}", pageQueryDTO);
        PageResult pageResult = categoryService.pageQuery(pageQueryDTO);
        return Result.success(pageResult);
    }

    @PostMapping
    public Result save(@RequestBody CategoryDTO categoryDTO) {
        log.info("新增分类: {}", categoryDTO);
        categoryService.insert(categoryDTO);
        return Result.success();
    }

    @PutMapping
    public Result updatte(@RequestBody CategoryDTO categoryDTO) {
        log.info("修改分类: {}", categoryDTO);
        categoryService.update(categoryDTO);
        return Result.success();
    }

    @PostMapping("/status/{status}")
    public Result startOrStop(@PathVariable Integer status, @RequestParam Long id) {
        log.info("启用或禁用分类: {} {}", status, id);
        categoryService.startOrStop(status, id);
        return Result.success();
    }

    @DeleteMapping
    public Result delete(@RequestParam Long id) {
        log.info("根据id删除分类: {}", id);
        categoryService.delete(id);
        return Result.success();
    }

    @GetMapping("/list")
    public Result<List<Category>> list(Integer type) {
        log.info("根据类型查询分类: {}", type);
        List<Category> categoryList = categoryService.listByType(type);
        return Result.success(categoryList);
    }


}
