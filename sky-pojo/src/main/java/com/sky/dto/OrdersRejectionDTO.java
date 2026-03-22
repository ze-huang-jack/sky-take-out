package com.sky.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

import java.io.Serializable;

@Data
public class OrdersRejectionDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @JsonAlias({"orderId", "ordersId"})
    private Long id;
    private String rejectionReason;
    private Integer status;
}
