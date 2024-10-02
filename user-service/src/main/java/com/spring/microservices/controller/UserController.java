package com.spring.microservices.controller;

import com.spring.microservices.model.User;
import com.spring.microservices.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/users")
    public List<User> createUsers(@RequestBody List<User> users){
        return userService.createUsers(users);
    }

    @PostMapping("/user")
    public User createUser(@RequestBody User user){
        return userService.createUser(user);
    }

    @PostMapping("/user/update")
    public User updateUser(@RequestBody User user){
        return userService.updateUser(user);
    }

    @GetMapping("/users")
    public List<User> retrieveUsers(){
        return userService.retrieveUsers();
    }

    @GetMapping("/user/{id}")
    public User retrieveUser(@PathVariable String id){
        return userService.retrieveUser(id);
    }
}
