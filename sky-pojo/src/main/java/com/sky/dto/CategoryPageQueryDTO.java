package com.sky.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryPageQueryDTO {
    private String name;
    private Integer type;
    private Integer page;
    private Integer pageSize;

}
