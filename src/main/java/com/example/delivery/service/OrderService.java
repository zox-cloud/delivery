package com.example.delivery.service;


import com.example.delivery.entity.Order;
import com.example.delivery.entity.OrderItem;
import com.example.delivery.entity.Users;
import com.example.delivery.repo.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {


    @Autowired
    private OrderRepository orderRepository;

    public List<Order> getOrderByCustomer(Users customer){
        return orderRepository.findByCustomer(customer);
    }


    public Order getOrderById(Long id){
        return orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Order not found"));
    }

    public Order saveOrder(Order order){
        double total_price = calculateTotalPrice(order.getOrderItems());
        order.setTotalPrice(total_price);
        return orderRepository.save(order);
    }

    public void deleteOrder(Long id){
        orderRepository.deleteById(id);
    }

    private double calculateTotalPrice(List<OrderItem> orderItems){
        return orderItems.stream()
                .mapToDouble(orderItem -> orderItem.getMenuItem().getPrice() * orderItem.getQuantity())
                .sum();
    }


}
