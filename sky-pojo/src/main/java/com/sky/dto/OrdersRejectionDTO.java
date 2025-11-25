package com.sky.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class OrdersRejectionDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String rejectionReason;
    private Integer status;
}
