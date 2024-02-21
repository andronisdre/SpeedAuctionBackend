package com.spring.SpeedAuction.Services;

import com.spring.SpeedAuction.Models.AuctionModels;
import com.spring.SpeedAuction.Models.OrderModels;
import com.spring.SpeedAuction.Repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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
        OrderModels existingOrders = orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order with id: " + id + " was not found!"));

        existingOrders.setId(updatedOrders.getId());
        existingOrders.setBooks(updatedOrders.getBooks());
        existingOrders.setId(updatedOrders.getId());
        existingOrders.setCreated_at(updatedOrders.getCreated_at());
        existingOrders.setReturned_at(updatedOrders.getReturned_at());

        return orderRepository.save(existingOrders);
    }




}
