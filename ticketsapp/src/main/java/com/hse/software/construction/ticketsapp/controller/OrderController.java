package com.hse.software.construction.ticketsapp.controller;

import com.hse.software.construction.ticketsapp.dto.CreateOrderRequest;
import com.hse.software.construction.ticketsapp.exception.OrderNotFoundException;
import com.hse.software.construction.ticketsapp.model.Order;
import com.hse.software.construction.ticketsapp.service.OrderServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/order")
@AllArgsConstructor
@Slf4j
public class OrderController {

    private OrderServiceImpl ticketService;

    @PostMapping("/create-order")
    public ResponseEntity<String> createOrder(@RequestBody CreateOrderRequest request) {
        try {
            log.info("request " + request);
            ticketService.createOrder(request);
            log.info("ok");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (NullPointerException e) {
            return ResponseEntity.badRequest().body("Invalid token");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok("Order created");
    }

    @GetMapping("/{orderId}")
    ResponseEntity<Order> getOrderById(@PathVariable Long orderId) {
        try {
            Order order = ticketService.getOrderById(orderId);
            return ResponseEntity.ok(order);
        } catch (OrderNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
