package com.sky.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SetmeaPageQueryDTO {
    private Long categoryId;
    private String name;
    private Integer page;
    private Integer pageSize;
    private Integer status;
}
