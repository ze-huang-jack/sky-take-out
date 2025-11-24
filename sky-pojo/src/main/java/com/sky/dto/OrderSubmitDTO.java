package com.sky.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderSubmitDTO {
    private Long addressBookId;
    private BigDecimal amount;
    private Integer deliveryStatus;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime estimatedDeliveryTime;
    private int packAmount;
    private Integer payMethod;
    private String remark;
    private int tablewareNumber;
    private Integer tablewareStatus;
}
