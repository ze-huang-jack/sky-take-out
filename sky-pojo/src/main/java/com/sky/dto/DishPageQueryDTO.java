package com.sky.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DishPageQueryDTO implements Serializable {
    private Long categoryId;
    private String name;
    private Integer status;
    private Integer page;
    private Integer pageSize;
}
