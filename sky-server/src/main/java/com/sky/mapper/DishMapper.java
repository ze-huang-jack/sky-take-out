package com.sky.mapper;

import com.sky.annotation.AutoFill;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.enumeration.OperationType;
import com.sky.vo.DishVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DishMapper {
//    @Select("select count(id) from dish where category_id = #{categoryId}")
//    Integer countByCategoryId(Long categoryId);

    /**
     * 分页查询菜品
     * @param queryDTO
     * @return
     */
    List<DishVO> pageQuery(DishPageQueryDTO queryDTO);

    /**
     * 新增菜品
     * @param dish
     */
    @AutoFill(value = OperationType.INSERT)
    void save(Dish dish);

    /**
     * 根据id查询单条菜品
     * @param id
     * @return
     */
    Dish getById(Long id);

    /**
     * 根据分类id查询n条菜品
     * @param categoryId
     * @return
     */
    List<Dish> listByCategoryId(Integer categoryId);

    /**
     * 修改菜品数据
     * @param dish
     */
    @AutoFill(value = OperationType.UPDATE)
    void update(Dish dish);

    /**
     * 根据id删除菜品
     * @param id
     */
    void deleteById(Long id);

    /**
     * 查询套餐内包含的菜品
     * @param id
     * @return
     */
    List<Dish> getBySetmealId(Long id);

    /**
     * 条件查询菜品
     * @param dish
     * @return
     */
    List<Dish> list(Dish dish);
}
