package com.sky.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderSubmitVO {
    private Long id;
    private BigDecimal orderAmount;
    private String orderNumber;
    private LocalDateTime orderTime;
}
