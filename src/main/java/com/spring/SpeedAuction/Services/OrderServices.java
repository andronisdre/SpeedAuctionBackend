package com.spring.SpeedAuction.Services;

import com.spring.SpeedAuction.Models.OrderModels;
import com.spring.SpeedAuction.Repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class OrderServices {

    @Autowired
    OrderRepository orderRepository;

    public OrderModels createOrder(OrderModels orders){
        return orderRepository.save(orders);
    }

    public List<OrderModels> getOrders() {
        return orderRepository.findAll();
    }

    public Optional<OrderModels> getOrderById(String id) {
        return orderRepository.findById(id);
    }

    public void deleteOrder(String id) {
        orderRepository.deleteById(id);
    }

    public OrderModels updateOrder(String id, OrderModels updatedOrders) {
        OrderModels existingOrder = orderRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Order with id: " + id + " was not found!"));

        existingOrder.setId(updatedOrders.getId());
        existingOrder.setOrder_finalized(updatedOrders.getOrder_finalized());
        existingOrder.setOrder_created(updatedOrders.getOrder_created());

        return orderRepository.save(existingOrder);
    }

}
