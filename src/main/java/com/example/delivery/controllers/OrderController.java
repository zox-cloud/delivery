package com.example.delivery.controllers;


import com.example.delivery.entity.Order;
import com.example.delivery.entity.Users;
import com.example.delivery.service.OrderItemService;
import com.example.delivery.service.OrderService;
import com.example.delivery.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UsersService usersService;



    @PostMapping("/create")
    public Order createOrder(@RequestBody Order order) {
        return orderService.saveOrder(order);
    }

//    @GetMapping("/{orderId}")
//    public String viewOrderDetails(@PathVariable Long orderId, Model model) {
//        Users customer = usersService.findByUserId(1L).orElseThrow(() -> new RuntimeException("User Not Found"));
//
//        Order order = orderService.getOrderById(orderId);
//
//        if (!order.getCustomer().getId().equals(customer.getId())) {
//            throw new RuntimeException("You are not authorized to view this order");
//        }
//
//        model.addAttribute("order", order);
//        return "orderDetails";
//    }


    @GetMapping("/{orderId}")
    public String viewOrderDetails(@PathVariable Long orderId, Model model) {
        Order order = orderService.getOrderById(orderId);

        model.addAttribute("order", order);
        return "orderDetails";
    }

    @GetMapping("/users/{userID}")
    public List<Order> getOrdersByCustomer(@PathVariable Long userID) {
        Optional<Users> user = usersService.findByUserId(userID);
        return user.map(orderService :: getOrderByCustomer).orElseThrow( () -> new RuntimeException("User not found"));
    }

    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable Long id){
        orderService.deleteOrder(id);
    }


}
