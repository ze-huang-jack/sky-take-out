package com.sky.controller.user;

import com.sky.entity.Category;
import com.sky.result.Result;
import com.sky.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Size;
import java.util.List;

@RestController("userCategoryController")
@Slf4j
@RequestMapping("/user/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    /**
     * 根据type查询分类
     *  1 = 菜品分类
     *  2 = 套餐分类
     * @return type
     */
    @GetMapping("/list")
    public Result<List<Category>> list(Integer type) {
        log.info("根据type查询分类: {}", type);
        List<Category> categoryList = categoryService.listByType(type);
        return Result.success(categoryList);
    }
}
