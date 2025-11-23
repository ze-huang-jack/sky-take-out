package com.sky.service;

import com.sky.dto.OrderSubmitDTO;
import com.sky.vo.OrderSubmitVO;
import org.springframework.stereotype.Service;

@Service
public interface OrderService {


    OrderSubmitVO submit(OrderSubmitDTO orderSubmitDTO);
}
