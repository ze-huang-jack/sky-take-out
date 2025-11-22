package com.sky.mapper;

import com.sky.annotation.AutoFill;
import com.sky.dto.SetmeaPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.enumeration.OperationType;
import com.sky.vo.SetmealVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SetmealMapper {

    /**
     * 根据菜品id查询关联的的套餐id
     * @param ids
     * @return
     */
    List<Long> getSetmealIdsByDishIds(List<Long> ids);

    List<SetmealVO> pageQuery(SetmeaPageQueryDTO queryDTO);

    /**
     * 新增套餐
     * @param setMeal
     */
    @AutoFill(value = OperationType.INSERT)
    void insert(Setmeal setMeal);

    /**
     * 根据id回显套餐
     * @param id
     * @return
     */
    Setmeal getById(Long id);

    /**
     * 根据id删除套餐
     * @param id
     */
    void deleteById(Long id);

    /**
     * 修改套餐
     * @param setmeal
     */
    @AutoFill(value = OperationType.UPDATE)
    void update(Setmeal setmeal);

    /**
     * 根据分类id查询套餐
     * @param categoryId
     * @return
     */
    List<Setmeal> getByCategoryId(Long categoryId);

    /**
     * 动态条件查询套餐
     * @param setmeal
     * @return
     */
    List<Setmeal> list(Setmeal setmeal);

//    @Select("select count(id) from setmeal where category_id = #{categoryId}")
//    Integer countByCategoryId(Long categoryId);
    
}
