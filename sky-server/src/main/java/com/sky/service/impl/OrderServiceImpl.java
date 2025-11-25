package com.sky.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.TreeCodec;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.context.BaseContext;
import com.sky.dto.OrdersConfirmDTO;
import com.sky.dto.OrdersPageQueryDTO;
import com.sky.dto.OrderSubmitDTO;
import com.sky.dto.OrdersRejectionDTO;
import com.sky.entity.*;
import com.sky.exception.OrderBusinessException;
import com.sky.mapper.*;
import com.sky.result.PageResult;
import com.sky.service.OrderService;
import com.sky.utils.WeChatPayUtil;
import com.sky.vo.OrderStatsVO;
import com.sky.vo.OrderSubmitVO;
import com.sky.vo.OrderVO;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private AddressBookMapper addressBookMapper;
    @Autowired
    private ShoppingCartMapper shoppingCartMapper;
    @Autowired
    private OrderDetailMapper orderDetailMapper;
    @Autowired
    private WeChatPayUtil weChatPayUtil;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private TreeCodec treeCodec;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public OrderSubmitVO submit(OrderSubmitDTO orderSubmitDTO) {
        // 异常处理： 收货地址为空 购物车为空
        AddressBook addressBook = addressBookMapper.getById(orderSubmitDTO.getAddressBookId());
        if (addressBook == null) {
            throw new OrderBusinessException("收货地址为空");
        }

        Long userId = BaseContext.getCurrentId();
        ShoppingCart queryCondition = new ShoppingCart();
        queryCondition.setUserId(userId);

        List<ShoppingCart> existCartList = shoppingCartMapper.list(queryCondition);
        if (existCartList == null || existCartList.isEmpty()) {
            throw new OrderBusinessException("购物车为空");
        }

        // 构造订单数据
        Orders order = new Orders();
        BeanUtils.copyProperties(orderSubmitDTO, order);
        order.setPhone(addressBook.getPhone());
        order.setAddress(addressBook.getDetail());
        order.setConsignee(addressBook.getConsignee());
        order.setNumber(String.valueOf(System.currentTimeMillis()));
        order.setUserId(userId);
//        order.setStatus(Orders.PENDING_PAYMENT);
        order.setStatus(Orders.TO_BE_CONFIRMED);
        order.setPayStatus(Orders.PAID);
//        order.setPayStatus(Orders.UN_PAID);
        order.setOrderTime(LocalDateTime.now());

        orderMapper.insertOrder(order);

        // 订单明细数据
        List<OrderDetail> orderDetailList = new ArrayList<>();
        for (ShoppingCart shoppingCart : existCartList) {
            OrderDetail orderDetail = new OrderDetail();
            BeanUtils.copyProperties(shoppingCart, orderDetail);
            orderDetail.setOrderId(order.getId());
            orderDetailList.add(orderDetail);
        }

        orderDetailMapper.insertBatch(orderDetailList);

        shoppingCartMapper.deleteAll(userId);

        OrderSubmitVO orderSubmitVO = OrderSubmitVO.builder().id(order.getId()).orderAmount(order.getAmount()).orderNumber(order.getNumber()).orderTime(order.getOrderTime()).build();

        return orderSubmitVO;
    }

    @Override
    public PageResult pageQuery(OrdersPageQueryDTO queryDTO) {
        PageHelper.startPage(queryDTO.getPage(), queryDTO.getPageSize());

        OrdersPageQueryDTO queryCondition = new OrdersPageQueryDTO();
        queryCondition.setUserId(BaseContext.getCurrentId());
        queryCondition.setStatus(queryDTO.getStatus());

        // 分页条件查询
        Page<Orders> ordersPage = orderMapper.pageQuery(queryCondition);

        List<OrderVO> list = new ArrayList<>();
        // 查询出订单明细，并封装入OrderVO进行响应
        if (ordersPage != null && ordersPage.getTotal() > 0) {
            for (Orders orders : ordersPage) {
                Long orderId = orders.getId();// 订单id

                // 查询订单明细
                List<OrderDetail> orderDetails = orderDetailMapper.getByOrderId(orderId);

                OrderVO orderVO = new OrderVO();
                BeanUtils.copyProperties(orders, orderVO);
                orderVO.setOrderDetailList(orderDetails);

                list.add(orderVO);
            }
        }
        return new PageResult(ordersPage.getTotal(), list);
    }

    /**
     * 查询订单详情
     *
     * @param id
     * @return
     */
    public OrderVO details(Long id) {
        Orders orders = orderMapper.getById(id);
        List<OrderDetail> orderDetailList = orderDetailMapper.getByOrderId(orders.getId());
        OrderVO orderVO = new OrderVO();
        BeanUtils.copyProperties(orders, orderVO);
        orderVO.setOrderDetailList(orderDetailList);
        return orderVO;
    }

    @Override
    public void userCancelById(Long id) throws Exception {
        Orders existOrder = orderMapper.getById(id);
        if (existOrder == null) {
            throw new OrderBusinessException("订单NOT FOUND");
        }
        //订单状态 1待付款 2待接单 3已接单 4派送中 5已完成 6已取消
        if(existOrder.getStatus() > 2) {
            throw new OrderBusinessException("订单状态错误");
        }

        Orders orders = new Orders();
        orders.setId(existOrder.getId());

        if(existOrder.getStatus() == Orders.TO_BE_CONFIRMED) {
//            weChatPayUtil.refund(
//                    existOrder.getNumber(), //商户订单号
//                    existOrder.getNumber(), //商户退款单号
//                    new BigDecimal(0.01),//退款金额，单位 元
//                    new BigDecimal(0.01));//原订单金额
            orders.setPayStatus(Orders.REFUND);
        }

        // 更新订单状态、取消原因、取消时间
        orders.setStatus(Orders.CANCELLED);
        orders.setCancelReason("用户取消");
        orders.setCancelTime(LocalDateTime.now());
        orderMapper.update(orders);
    }

    @Override
    public void repetition(Long id) {
        // 查询当前用户id
        Long userId = BaseContext.getCurrentId();

        // 根据订单id查询当前订单详情
        List<OrderDetail> orderDetailList = orderDetailMapper.getByOrderId(id);

        // 将订单详情对象转换为购物车对象
        List<ShoppingCart> shoppingCartList = orderDetailList.stream().map(x -> {
            ShoppingCart shoppingCart = new ShoppingCart();

            // 将原订单详情里面的菜品信息重新复制到购物车对象中
            BeanUtils.copyProperties(x, shoppingCart, "id");
            shoppingCart.setUserId(userId);
            shoppingCart.setCreateTime(LocalDateTime.now());

            return shoppingCart;
        }).collect(Collectors.toList());

        // 将购物车对象批量添加到数据库
        shoppingCartMapper.insertBatch(shoppingCartList);
    }

    @Override
    public PageResult conditionSearch(OrdersPageQueryDTO ordersPageQueryDTO) {
        PageHelper.startPage(ordersPageQueryDTO.getPage(), ordersPageQueryDTO.getPageSize());
        Page<Orders> page = orderMapper.pageQuery(ordersPageQueryDTO);
        List<OrderVO> list = getOrderVOList(page);
        return new PageResult(page.getTotal(), list);
    }

    @Override
    public OrderStatsVO stats() {
        Integer beforeTakingOrder = orderMapper.countStatus(Orders.TO_BE_CONFIRMED);
        Integer beforeDelivering = orderMapper.countStatus(Orders.CONFIRMED);
        Integer delivering = orderMapper.countStatus(Orders.DELIVERY_IN_PROGRESS);

        OrderStatsVO orderStatsVO = new OrderStatsVO();
        orderStatsVO.setBeforeTakingOrder(beforeTakingOrder);
        orderStatsVO.setBeforeDelivering(beforeDelivering);
        orderStatsVO.setDelivering(delivering);
        return orderStatsVO;
    }

    @Override
    public void confirm(OrdersConfirmDTO ordersConfirmDTO) {
        if(ordersConfirmDTO.getId() == null) {
            throw new OrderBusinessException("id为null");
        }
        Orders orders = Orders.builder()
                .id(ordersConfirmDTO.getId())
                .status(Orders.CONFIRMED)
                .build();
        orderMapper.update(orders);
    }

    @Override
    public void reject(OrdersRejectionDTO ordersRejectionDTO) {
        Orders order = orderMapper.getById(ordersRejectionDTO.getId());
        // 订单只有存在且状态为2（待接单）才可以拒单
        if (order == null || !order.getStatus().equals(Orders.TO_BE_CONFIRMED)) {
            log.info("{} {}", order, order.getStatus());
            throw new OrderBusinessException("订单状态错误");
        }
        Integer payStatus = order.getPayStatus();
        Orders orders = new Orders();
        if(payStatus.equals(Orders.PAID)) {
            log.info("申请退款");
            orders.setPayStatus(Orders.REFUND);
        }

        orders.setId(order.getId());
        orders.setStatus(Orders.CANCELLED);
        orders.setRejectionReason(ordersRejectionDTO.getRejectionReason());
        orders.setCancelTime(LocalDateTime.now());
        orderMapper.update(orders);
    }


    private List<OrderVO> getOrderVOList(Page<Orders> page) {
        List<OrderVO> orderVOList = new ArrayList<>();
        List<Orders> ordersList = page.getResult();
        for(Orders orders : ordersList) {
            OrderVO orderVO = new OrderVO();
            BeanUtils.copyProperties(orders, orderVO);
            String orderDishes = getOrderDishesStr(orders);

            orderVO.setOrderDishes(orderDishes);
            orderVOList.add(orderVO);
        }
        return orderVOList;
    }

    private String getOrderDishesStr(Orders orders) {
        // 查询订单菜品详情信息（订单中的菜品和数量）
        List<OrderDetail> orderDetailList = orderDetailMapper.getByOrderId(orders.getId());

        // 将每一条订单菜品信息拼接为字符串（格式：宫保鸡丁*3；）
        List<String> orderDishList = orderDetailList.stream().map(x -> {
            String orderDish = x.getName() + "*" + x.getNumber() + ";";
            return orderDish;
        }).collect(Collectors.toList());

        // 将该订单对应的所有菜品信息拼接在一起
        return String.join("", orderDishList);
    }

}
