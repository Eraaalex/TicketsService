package com.hse.software.construction.ticketsapp.service;

import com.hse.software.construction.ticketsapp.model.Order;
import org.springframework.stereotype.Service;

@Service
public interface OrderProcessService {
    void addOrderToProcessing(Order order);
}
