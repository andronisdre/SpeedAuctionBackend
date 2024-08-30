package com.spring.SpeedAuction.security.Services;

import com.spring.SpeedAuction.dto.OrderDto;

import com.spring.SpeedAuction.Models.AuctionModels;
import com.spring.SpeedAuction.Models.OrderModels;
import com.spring.SpeedAuction.Models.UserModels;
import com.spring.SpeedAuction.Repository.AuctionRepository;
import com.spring.SpeedAuction.Repository.OrderRepository;
import com.spring.SpeedAuction.Repository.UserRepository;
import com.spring.SpeedAuction.dto.OrderResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderServices {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    AuctionRepository auctionRepository;

    @Autowired
    UserRepository userRepository;


    public OrderModels createOrder(OrderDto orderDto) {

        // ###### BRYTA NER TILL TRE OLIKA METODER
        UserModels user = userRepository.findById(orderDto.getSellerid())
                .orElseThrow(() -> new IllegalArgumentException("Invalid user id"));

        UserModels buyer = userRepository.findById(orderDto.getBuyerid())
                .orElseThrow(() -> new IllegalArgumentException("Invalid user id"));

        AuctionModels auction = auctionRepository.findById(orderDto.getAuctionid())
                .orElseThrow(() -> new IllegalArgumentException("Invalid auction id"));

        OrderModels NewOrder = new OrderModels();
        NewOrder.setBuyer_id(buyer);
        NewOrder.setSeller_id(user);
        NewOrder.setAuction_id(auction);
        NewOrder.setOrder_created(orderDto.getCreated_at());

        AuctionModels existingAuction = auctionRepository.findById
                (NewOrder.getAuction_id().getId()).orElseThrow(() -> new IllegalArgumentException("auction does not exist"));
        existingAuction.setActive(false);
        // #########

        auctionRepository.save(existingAuction);

        return  orderRepository.save(NewOrder);
    }

    private OrderResponse convertToDto(OrderModels orderModels) {

        OrderResponse orderesponse = new OrderResponse();
        orderesponse.setAuctionId(orderModels.getAuction_id().getId());
        orderesponse.setBuyerId(orderModels.getBuyer_id().getId());
        orderesponse.setSellerId(orderModels.getSeller_id().getId());
        orderesponse.setCreated_at(orderModels.getOrder_created());

        return orderesponse;
    }

    public List<OrderResponse> getAllOrder(){
         List<OrderModels> orders = orderRepository.findAll();
         return orders.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public List<OrderResponse> getOrderById(String id) {
         Optional<OrderModels> orders = orderRepository.findById(id);
         return orders.stream().map(this::convertToDto).collect(Collectors.toList());
    }
    public void deleteOrder(String id) {
        OrderModels orderModels = orderRepository.findById(id).orElse(null);
        if (orderModels != null) {
            orderRepository.deleteById(id);
        }
        else {
            throw new IllegalArgumentException("order id doesnt exist");
        }
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

                    return orderRepository.save(existingOrderModels);
                })
                .orElseThrow(() -> new NoSuchElementException("User not found with id: " + id));
    }
}
