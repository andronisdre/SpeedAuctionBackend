package com.spring.SpeedAuction.Controller;

import com.spring.SpeedAuction.Models.OrderModels;
import com.spring.SpeedAuction.Repository.OrderRepository;
import com.spring.SpeedAuction.Services.OrderServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

public class OrderController {

    @Autowired
    OrderServices orderService;

    @PostMapping
    public ResponseEntity<OrderModels> createOrders(@RequestBody OrderModels orders) {
        OrderModels createdOrders = orderService.createOrder(orders);
        return new ResponseEntity<>(createdOrders, HttpStatus.CREATED);//ResponseEntity.status(HttpStatus.CREATED).body(createdOrders);
    }

    @GetMapping("/all")
    public List<OrderModels> getAllOrders() {
        return orderService.getOrders();
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderModels> getOrdersById(@PathVariable String id) {
        Optional<OrderModels> orders = orderService.getOrderById(id);
        return orders.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderModels> updateOrders(@PathVariable String id, @RequestBody OrderModels ordersDetails) {
        OrderModels updatedOrders = orderService.updateOrder(id, ordersDetails);
        return ResponseEntity.ok(updatedOrders);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOrder(@PathVariable String id) {
        orderService.deleteOrder(id);
        return ResponseEntity.ok("Orders with id: " + id + " has been deleted!");
    }






}
