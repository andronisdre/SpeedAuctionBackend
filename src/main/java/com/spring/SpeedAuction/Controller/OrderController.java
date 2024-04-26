package com.spring.SpeedAuction.Controller;

import com.spring.SpeedAuction.dto.OrderDto;

import com.spring.SpeedAuction.Models.OrderModels;
import com.spring.SpeedAuction.dto.OrderResponse;
import com.spring.SpeedAuction.security.Services.OrderServices;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("api/order")
public class OrderController {

    @Autowired
    OrderServices orderService;

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN') or hasRole('MODERATOR')")
    @PostMapping
    public ResponseEntity<OrderModels> createOrder(@RequestBody OrderDto orderDto) {
        OrderModels createdOrders = orderService.createOrder(orderDto);
        return new ResponseEntity<>(createdOrders, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN') or hasRole('MODERATOR')")
    @GetMapping("/all")
    public ResponseEntity<List<OrderResponse>> getAllOrder() {
        List<OrderResponse> orders = orderService.getAllOrder();
        return ResponseEntity.ok(orders);
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN') or hasRole('MODERATOR')")
    @GetMapping("/{id}")
    public ResponseEntity<List<OrderResponse>> getOrderById(@PathVariable String id) {
        List<OrderResponse> orders = orderService.getOrderById(id);
        return ResponseEntity.ok(orders);
    }
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
    @PutMapping("/{id}")
    public ResponseEntity<OrderModels> updateOrder(@PathVariable String id,@Valid @RequestBody OrderModels ordersDetails) {
        OrderModels updatedOrders = orderService.updateOrder(id, ordersDetails);
        return ResponseEntity.ok(updatedOrders);
    }
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOrder(@PathVariable String id) {
        orderService.deleteOrder(id);
        return ResponseEntity.ok("Orders with id: " + id + " has been deleted!");
    }
}
