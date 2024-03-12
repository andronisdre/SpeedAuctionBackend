package com.spring.SpeedAuction.Controller;

import com.spring.SpeedAuction.DTO.OrderDto;
import com.spring.SpeedAuction.DTO.OrderResponse;
import com.spring.SpeedAuction.Models.OrderModels;
import com.spring.SpeedAuction.Services.OrderServices;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/order")
public class OrderController {

    @Autowired
    OrderServices orderService;

    @PostMapping
    public ResponseEntity<OrderModels> createOrder(@RequestBody OrderDto orderDto) {
        OrderModels createdOrders = orderService.createOrder(orderDto);
        return new ResponseEntity<>(createdOrders, HttpStatus.CREATED);//ResponseEntity.status(HttpStatus.CREATED).body(createdOrders);
    }

    @GetMapping("/all")
    public ResponseEntity<List<OrderResponse>> getAllOrder() {
        List<OrderResponse> orders = orderService.getAllOrder();
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<OrderResponse>> getOrderById(@PathVariable String id) {
        List<OrderResponse> orders = orderService.getOrderById(id);
        return ResponseEntity.ok(orders);
    }
    @PutMapping("/{id}")
    public ResponseEntity<OrderModels> updateOrder(@PathVariable String id,@Valid @RequestBody OrderModels ordersDetails) {
        OrderModels updatedOrders = orderService.updateOrder(id, ordersDetails);
        return ResponseEntity.ok(updatedOrders);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOrder(@PathVariable String id) {
        orderService.deleteOrder(id);
        return ResponseEntity.ok("Orders with id: " + id + " has been deleted!");
    }


}
