package com.spring.SpeedAuction.Controller;

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
    public ResponseEntity<OrderModels> createOrder(@RequestBody OrderModels orders) {
        OrderModels createdOrders = orderService.createOrder(orders);
        return new ResponseEntity<>(createdOrders, HttpStatus.CREATED);//ResponseEntity.status(HttpStatus.CREATED).body(createdOrders);
    }

    @GetMapping("/all")
    public List<OrderModels> getAllOrder() {
        return orderService.getOrders();
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderModels> getOrderById(@PathVariable String id) {
        Optional<OrderModels> orders = orderService.getOrderById(id);
        return orders.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
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
