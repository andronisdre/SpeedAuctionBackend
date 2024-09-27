package com.spring.SpeedAuction.Security.Services;

import com.spring.SpeedAuction.DTO.OrderDto;
import com.spring.SpeedAuction.DTO.OrderResponse;
import com.spring.SpeedAuction.Models.AuctionModels;
import com.spring.SpeedAuction.Models.OrderModels;
import com.spring.SpeedAuction.Models.UserModels;
import com.spring.SpeedAuction.Repository.AuctionRepository;
import com.spring.SpeedAuction.Repository.OrderRepository;
import com.spring.SpeedAuction.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
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
        UserModels seller = getUserById(orderDto.getSellerid(), "Invalid seller id");
        UserModels buyer = getUserById(orderDto.getBuyerid(), "Invalid buyer id");
        AuctionModels auction = getAuctionById(orderDto.getAuctionid(), "Invalid auction id");

        OrderModels newOrder = createAndSaveOrder(seller, buyer, auction, orderDto.getCreated_at());
        deactivateAuction(auction.getId());

        return newOrder;
    }

    // Hämta användare
    private UserModels getUserById(String userId, String errorMessage) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException(errorMessage));
    }

    // Hämta auktion
    private AuctionModels getAuctionById(String auctionId, String errorMessage) {
        return auctionRepository.findById(auctionId)
                .orElseThrow(() -> new IllegalArgumentException(errorMessage));
    }

    // Skapa och spara order
    private OrderModels createAndSaveOrder(UserModels seller, UserModels buyer, AuctionModels auction, Date createdAt) {
        OrderModels newOrder = new OrderModels();
        newOrder.setBuyer_id(buyer);
        newOrder.setSeller_id(seller);
        newOrder.setAuction_id(auction);
        newOrder.setOrder_created(createdAt);

        return orderRepository.save(newOrder);
    }

    // Inaktivera auktion
    private void deactivateAuction(String auctionId) {
        AuctionModels existingAuction = auctionRepository.findById(auctionId)
                .orElseThrow(() -> new IllegalArgumentException("Auction does not exist"));
        existingAuction.setActive(false);
        auctionRepository.save(existingAuction);
    }

    private OrderResponse convertToDto(OrderModels orderModels) {
        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setAuctionId(orderModels.getAuction_id().getId());
        orderResponse.setBuyerId(orderModels.getBuyer_id().getId());
        orderResponse.setSellerId(orderModels.getSeller_id().getId());
        orderResponse.setCreated_at(orderModels.getOrder_created());
        return orderResponse;
    }

    public List<OrderResponse> getAllOrder() {
        List<OrderModels> orders = orderRepository.findAll();
        return orders.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public OrderResponse getOrderById(String id) {
        Optional<OrderModels> orderOptional = orderRepository.findById(id);
        OrderModels order = orderOptional.orElseThrow(() -> new NoSuchElementException("Order not found with id: " + id));
        return convertToDto(order);
    }
    public void deleteOrder(String id) {
        OrderModels orderModels = orderRepository.findById(id).orElse(null);
        if (orderModels != null) {
            orderRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Order id doesn't exist");
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
