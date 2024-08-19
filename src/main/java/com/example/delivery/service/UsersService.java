package com.example.delivery.service;


import com.example.delivery.entity.Role;
import com.example.delivery.entity.Users;
import com.example.delivery.repo.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsersService {


    @Autowired
    private UsersRepository usersRepository;

    public void registerUser(Users user) {
        if (user.getRole() == null) {
            user.setRole(Role.CUSTOMER); // Default role if not set
        }
        usersRepository.save(user);
    }


    public Users saveUsers(Users user){
        return usersRepository.save(user);
    }

    public Optional<Users> findByUserId(Long id){
        return usersRepository.findById(id);
    }

    public Optional<Users> findByUsername(String username){
        return usersRepository.findByUsername(username);
    }

    public  void deleteUser(Long id){
        usersRepository.deleteById(id);
    }


    public boolean checkPassword(Users user, String rawPassword) {
        return user.getPassword().equals(rawPassword);
    }





}
