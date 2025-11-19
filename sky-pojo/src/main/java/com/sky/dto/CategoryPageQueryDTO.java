package com.sky.dto;

import lombok.*;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryPageQueryDTO implements Serializable{
    private String name;
    private Integer type;
    private Integer page;
    private Integer pageSize;

}
