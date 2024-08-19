package com.example.delivery.controllers;


import com.example.delivery.entity.Users;
import com.example.delivery.service.UsersService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


//todo simple crud
@Controller
@RestController
@RequestMapping("/users")


public class UsersController {

    @Autowired
    private UsersService usersService;


    @PostMapping("/register")
    public String registerUser(@RequestBody  Users users){
        usersService.saveUsers(users);
        return "User saved successfully";
    }

//    @PostMapping("/logins")
//    public String loginUser(@ModelAttribute Users users, Model model, HttpSession session) {
//        Optional<Users> existingUser = usersService.findByUsername(users.getUsername());
//        if (existingUser.isPresent() && existingUser.get().getPassword().equals(users.getPassword())) {
//            session.setAttribute("loggedInUser", existingUser.get());
//            return "You logged successfully";
//        } else {
//            model.addAttribute("error", "Invalid username or password");
//            return "Error ";
//        }
//    }


    @GetMapping("/{id}")
    public Optional<Users> getUserById(@PathVariable Long id){
        return usersService.findByUserId(id);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id){
        usersService.deleteUser(id);
    }


}
