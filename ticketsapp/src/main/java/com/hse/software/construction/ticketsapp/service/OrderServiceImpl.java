package com.hse.software.construction.ticketsapp.service;

import com.hse.software.construction.ticketsapp.dto.CreateOrderRequest;
import com.hse.software.construction.ticketsapp.exception.OrderNotFoundException;
import com.hse.software.construction.ticketsapp.model.Order;
import com.hse.software.construction.ticketsapp.model.OrderStatus;
import com.hse.software.construction.ticketsapp.model.Station;
import com.hse.software.construction.ticketsapp.repository.OrderRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;

@Service
@AllArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {


    private final OrderRepository orderRepository;
    private final StationService stationService;
    private final OrderProcessService orderProcessService;

    @Override
    public Order createOrder(CreateOrderRequest request) {
        if (Objects.equals(request.getFromStationId(), request.getToStationId())) {
            throw new IllegalArgumentException("From and to stations should be different" + request);
        }
        Station fromStation = stationService.getStationById(request.getFromStationId());
        Station toStation = stationService.getStationById(request.getToStationId());

        if (fromStation == null || toStation == null) {
            log.info("Stations not found");
            throw new IllegalArgumentException("Stations should exist");
        }

        Order order = new Order();
        order.setUserId(request.getUserId());
        order.setStatus(OrderStatus.CHECK.ordinal());
        order.setCreated(new Date());
        order.setFromStationId(fromStation.getId());
        order.setToStationId(toStation.getId());

        log.info("Order created: {}", order);
        orderRepository.save(order);
        orderProcessService.addOrderToProcessing(order);

        return order;
    }

    @Override
    public Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId).orElseThrow(() -> new OrderNotFoundException("Order not found"));
    }
}
