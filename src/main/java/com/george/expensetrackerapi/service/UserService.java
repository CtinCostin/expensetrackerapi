package com.george.expensetrackerapi.service;

import com.george.expensetrackerapi.entity.User;
import com.george.expensetrackerapi.entity.UserModel;

public interface UserService {

    User createUser(UserModel user);
}
