package com.george.expensetrackerapi.service;

import com.george.expensetrackerapi.entity.User;
import com.george.expensetrackerapi.entity.UserModel;

import java.util.List;

public interface UserService {

    User createUser(UserModel user);

    User readUser();

    List<User> readAllUsers();

    User updateUser(UserModel user);

    void deleteUser();

    User getLoggedInUser();


}
