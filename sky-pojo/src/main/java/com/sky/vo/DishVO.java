package com.sky.vo;

import com.sky.entity.DishFlavor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DishVO {
    private Long id;
    private String name;
    private Long categoryId;
    private BigDecimal price;
    private String image;;
    private String description;
    private Integer status;
    private LocalDateTime updateTime;
    private String categoryName;
    private List<DishFlavor> flavors = new ArrayList<>();
}
