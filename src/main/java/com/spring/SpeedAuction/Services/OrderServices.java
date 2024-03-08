package com.spring.SpeedAuction.Services;

import com.spring.SpeedAuction.DTO.OrderDto;
import com.spring.SpeedAuction.Models.AuctionModels;
import com.spring.SpeedAuction.Models.OrderModels;
import com.spring.SpeedAuction.Models.UserModels;
import com.spring.SpeedAuction.Repository.AuctionRepository;
import com.spring.SpeedAuction.Repository.OrderRepository;
import com.spring.SpeedAuction.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class OrderServices {
    //HELENA:
    // ni bör kunna fixa order nu efter det vi gått igenom på lektionerna så att ni får
    // en ref till en User och en Auction
    @Autowired
    OrderRepository orderRepository;

    @Autowired
    AuctionRepository auctionRepository;

    @Autowired
    UserRepository userRepository;


    /*public OrderModels createOrder(OrderModels orders){
        return orderRepository.save(orders);
    }*/

    public OrderModels createOrder(OrderDto orderDto) {

        UserModels user = userRepository.findById(orderDto.getUser_id())
                .orElseThrow(() -> new IllegalArgumentException("Invalid user id"));

        List<AuctionModels> auctions_id = new ArrayList<>();
        for (String auction_id : orderDto.getAuction_id() {
            auctions_id.add(auctionRepository.findById(auction_id)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid auction id")));
        }

        OrderModels orders = new OrderModels();
        orders.setId(String.valueOf(user));
        orders.setSeller_id();
        orders.setOrder_created(orderDto.getCreated_at());

        return  orderRepository.save(orders);
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

    public OrderModels updateOrder(String id, OrderModels updateOrder) {
        return orderRepository.findById(id)
                .map(existingOrderModels -> {
                    if (updateOrder.getSeller_id() != null) {
                        existingOrderModels.setSeller_id(updateOrder.getSeller_id());
                    }
                    if (updateOrder.getBuyer_id() != null) {
                        existingOrderModels.setBuyer_id(updateOrder.getBuyer_id());
                    }
                    if (updateOrder.getAuction_id() != null) {
                        existingOrderModels.setAuction_id(updateOrder.getAuction_id());
                    }
                    if (updateOrder.getOrder_created() != null) {
                        existingOrderModels.setOrder_created(updateOrder.getOrder_created());
                    }
                    if (updateOrder.getOrder_finalized() != null) {
                        existingOrderModels.setOrder_finalized(updateOrder.getOrder_finalized());
                    }

                    return orderRepository.save(updateOrder);
                })
                .orElseThrow(() -> new NoSuchElementException("User not found with id: " + id));
    }
}
