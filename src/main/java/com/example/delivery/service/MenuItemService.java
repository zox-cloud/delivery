package com.example.delivery.service;


import com.example.delivery.entity.MenuItem;
import com.example.delivery.repo.MenuItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuItemService {

    @Autowired
    private MenuItemRepository menuItemRepository;


    public List<MenuItem> getMenuItemsByRestaurantId(Long restaurantId) {
        return menuItemRepository.findAll().stream()
                .filter(menuItem -> menuItem.getRestaurant().getId().equals(restaurantId))
                .toList();
    }

    public MenuItem getMenuItemById(Long id) {
        return menuItemRepository.findById(id).orElseThrow( () -> new RuntimeException("Menu item not found"));
    }

    public MenuItem saveMenuItem(MenuItem menuItem) {
        return menuItemRepository.save(menuItem);
    }

    public void deleteMenuItem(Long id) {
        menuItemRepository.deleteById(id);
    }

}
