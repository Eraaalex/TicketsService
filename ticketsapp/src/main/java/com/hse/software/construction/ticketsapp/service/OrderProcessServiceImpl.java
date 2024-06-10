package com.hse.software.construction.ticketsapp.service;

import com.hse.software.construction.ticketsapp.model.Order;
import com.hse.software.construction.ticketsapp.model.OrderStatus;
import com.hse.software.construction.ticketsapp.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Service
public class OrderProcessServiceImpl implements OrderProcessService {
    private final OrderRepository orderRepository;
    private final Queue<Order> orderQueue = new ConcurrentLinkedQueue<>();
    private final ExecutorService executor = Executors.newFixedThreadPool(5);

    private final int maxBatchSize = 3;


    public OrderProcessServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public void addOrderToProcessing(Order order) {
        if (order == null) {
            throw new IllegalArgumentException("Order cannot be null");
        }
        if (order.getStatus() == OrderStatus.CHECK.ordinal()) {
            orderQueue.add(order);
            processOrders(); // Обработка заказов сразу после добавления
        } else {
            throw new IllegalArgumentException("Order has already been processed with status: " + OrderStatus.values()[order.getStatus()]);
        }
    }

    private void processOrders() {
        List<Order> ordersToProcess = new ArrayList<>();
        while (!orderQueue.isEmpty() && ordersToProcess.size() < maxBatchSize) {
            Order order = orderQueue.poll();
            if (order != null) {
                ordersToProcess.add(order);
            }
        }

        for (Order order : ordersToProcess) {
            executor.submit(() -> processOrder(order));
        }
    }

    private void processOrder(Order order) {
        try {
            TimeUnit.SECONDS.sleep(10); // Имитация обработки заказа
            order.setStatus(OrderStatus.SUCCESSFUL.ordinal());
            orderRepository.save(order);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}