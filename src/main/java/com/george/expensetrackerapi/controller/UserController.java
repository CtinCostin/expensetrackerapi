package com.george.expensetrackerapi.controller;

import com.george.expensetrackerapi.entity.User;
import com.george.expensetrackerapi.entity.UserModel;
import com.george.expensetrackerapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<User> createUser(@RequestBody UserModel user) {
        return new ResponseEntity<>(userService.createUser(user), HttpStatus.CREATED);
    }
}
