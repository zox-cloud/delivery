package com.example.delivery.controllers;


//todo simple crud

import com.example.delivery.entity.MenuItem;
import com.example.delivery.service.MenuItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/menuItems")
public class MenuItemController {


    @Autowired
    private MenuItemService menuItemService;

    @GetMapping("/restaurant/{restaurant_ID}")
    public List<MenuItem> getMenuItemsByRestaurant(@PathVariable Long restaurant_ID) {
        return menuItemService.getMenuItemsByRestaurantId(restaurant_ID);
    }


    @GetMapping("/{id}")
    public MenuItem getMenuItemById(@PathVariable Long id) {
        return menuItemService.getMenuItemById(id);
    }


    @PostMapping("/add")
    public MenuItem addMenuItem(@RequestBody MenuItem menuItem){
        return menuItemService.saveMenuItem(menuItem);
    }

    @DeleteMapping("/{id}")
    public void deleteMenuItem(@PathVariable Long id) {
        menuItemService.deleteMenuItem(id);
    }




}
