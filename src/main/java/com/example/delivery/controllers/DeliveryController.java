package com.example.delivery.controllers;


import com.example.delivery.entity.*;
import com.example.delivery.service.MenuItemService;
import com.example.delivery.service.OrderService;
import com.example.delivery.service.RestaurantService;
import com.example.delivery.service.UsersService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class DeliveryController {
    @Autowired
    private UsersService usersService;

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private MenuItemService menuItemService;

    @Autowired
    private OrderService orderService;




    @GetMapping("/register")
    public String showRegisterPage(Model model){
        model.addAttribute("users", new Users());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute Users users){
        usersService.saveUsers(users);
        return "redirect:/logins";
    }

    @GetMapping("/logins")
    public String showLoginForm(Model model) {
        model.addAttribute("users", new Users());
        return "login";
    }


    @PostMapping("/logins")
    public String loginUser(@ModelAttribute Users users, Model model, HttpSession session) {
        Optional<Users> existingUser = usersService.findByUsername(users.getUsername());
        if (existingUser.isPresent() && existingUser.get().getPassword().equals(users.getPassword())) {
            session.setAttribute("loggedInUser", existingUser.get());
            return "redirect:/res";
        } else {
            model.addAttribute("error", "Invalid username or password");
            return "login";
        }
    }

    @GetMapping("/res")
    public String getRestaurants(Model model) {
        List<Restaurant> restaurants = restaurantService.getAllRestaurants();
        model.addAttribute("restaurants", restaurants);
        return "index";
    }

    @PostMapping("/order")
    public String placeOrder(@RequestParam("menuItemIds") List<Long> menuItemIds,
                             @RequestParam("quantities") List<Integer> quantities,
                             @RequestParam("userId") Long userId) {
        Users customer = usersService.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("User Not Found"));

        Order order = new Order();
        order.setCustomer(customer);
        order.setStatus(OrderStatus.PENDING);

        List<OrderItem> orderItems = IntStream.range(0, menuItemIds.size())
                .mapToObj(i -> {
                    Long menuItemId = menuItemIds.get(i);
                    Integer quantity = quantities.get(i);

                    MenuItem menuItem = menuItemService.getMenuItemById(menuItemId);
                    OrderItem orderItem = new OrderItem();
                    orderItem.setMenuItem(menuItem);
                    orderItem.setOrder(order);
                    orderItem.setQuantity(quantity);
                    return orderItem;
                }).collect(Collectors.toList());

        order.setOrderItems(orderItems);
        orderService.saveOrder(order);

        return "redirect:/orders/" + order.getId();
    }




    @GetMapping("/restaurant/{restaurantId}")
    public String viewMenu(@PathVariable Long restaurantId, Model model, HttpSession session) {
        Users loggedInUser = (Users) session.getAttribute("loggedInUser");
        Restaurant restaurant = restaurantService.getRestaurantById(restaurantId);


        List<MenuItem> menuItems = menuItemService.getMenuItemsByRestaurantId(restaurantId);
        model.addAttribute("restaurant", restaurant);
        model.addAttribute("menuItems", menuItems);
        model.addAttribute("loggedInUser", loggedInUser); // Make sure the loggedInUser is added to the model

        return "restaurant";
    }

    @GetMapping("/orders/confirmation")
    public String orderConfirmation() {
        return "menu";
    }

    @GetMapping("/nothing")
    public String hello(){
        return "new commit";
    }


}
