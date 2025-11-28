package com.sky.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class OrderStatsVO implements Serializable {
        //待接单数量
        private Integer beforeTakingOrder;

        //待派送数量
        private Integer beforeDelivering;

        //派送中数量
        private Integer delivering;
}
