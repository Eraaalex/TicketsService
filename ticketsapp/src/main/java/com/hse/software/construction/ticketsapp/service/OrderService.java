package com.hse.software.construction.ticketsapp.service;

import com.hse.software.construction.ticketsapp.dto.CreateOrderRequest;
import com.hse.software.construction.ticketsapp.model.Order;
import org.springframework.stereotype.Service;

@Service
public interface OrderService {

    Order createOrder(CreateOrderRequest request);

    Order getOrderById(Long orderId);
}
