package com.george.expensetrackerapi.entity;

import lombok.Data;

@Data
public class UserModel {


    private String name;

    private String email;

    private String password;

    private Integer age = 0;
}
