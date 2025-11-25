package com.sky.service;

import com.sky.dto.OrdersConfirmDTO;
import com.sky.dto.OrdersPageQueryDTO;
import com.sky.dto.OrderSubmitDTO;
import com.sky.dto.OrdersRejectionDTO;
import com.sky.result.PageResult;
import com.sky.vo.OrderStatsVO;
import com.sky.vo.OrderSubmitVO;
import com.sky.vo.OrderVO;
import org.springframework.stereotype.Service;

@Service
public interface OrderService {


    OrderSubmitVO submit(OrderSubmitDTO orderSubmitDTO);

    PageResult pageQuery(OrdersPageQueryDTO queryDTO);

    OrderVO details(Long id);

    void userCancelById(Long id) throws Exception;

    /**
     * 再来一单
     * @param id
     */
    void repetition(Long id);

    PageResult conditionSearch(OrdersPageQueryDTO ordersPageQueryDTO);

    OrderStatsVO stats();

    void confirm(OrdersConfirmDTO ordersConfirmDTO);


    void reject(OrdersRejectionDTO ordersRejectionDTO);
}
