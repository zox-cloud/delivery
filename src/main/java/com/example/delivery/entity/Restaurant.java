package com.example.delivery.entity;


import jakarta.persistence.*;

import java.util.List;

@Entity
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String restaurantName;
    private String restaurantAddress;
    private String restaurantRating;
    private String restaurantDescription;

    @OneToMany(mappedBy = "restaurant" , cascade = CascadeType.ALL)
    private List<MenuItem> menuItems;


    public Long getId() {
        return id;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public String getRestaurantAddress() {
        return restaurantAddress;
    }

    public String getRestaurantRating() {
        return restaurantRating;
    }

    public String getRestaurantDescription() {
        return restaurantDescription;
    }

    public List<MenuItem> getMenuItems() {
        return menuItems;
    }
}
