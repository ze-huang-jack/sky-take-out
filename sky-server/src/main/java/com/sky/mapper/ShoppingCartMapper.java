package com.sky.mapper;

import com.sky.entity.ShoppingCart;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ShoppingCartMapper {

    /**
     * 插入购物车数据
     * @param shoppingCart
     */
    void insertShoppingCart(ShoppingCart shoppingCart);

    /**
     * 条件查询购物车数据
     * @param shoppingCart
     * @return
     */
    List<ShoppingCart> list(ShoppingCart shoppingCart);

    /**
     * 更新商品数量
     * @param shoppingCart
     */
    @Update("update shopping_cart set number = #{number} where id = #{id}")
    void updateNumberById(ShoppingCart shoppingCart);

    /**
     * 删除该用户的所有购物车数据
     */
    void deleteAll(Long userId);

    /**
     * 通过id删除购物车数据
     * @param id
     */
    void deleteById(Long id);
}
