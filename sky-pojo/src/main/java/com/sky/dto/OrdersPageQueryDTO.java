package com.sky.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrdersPageQueryDTO {
    private Integer page;
    private Integer pageSize;
    private Integer status;
    private Long userId;
}
