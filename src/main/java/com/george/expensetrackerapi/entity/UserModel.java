package com.george.expensetrackerapi.entity;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class UserModel {


    @NotBlank(message = "The name should not be empty")
    private String name;

    @NotBlank(message = "Email should not be empty")
    @Email(message = "Enter a valid email")
    private String email;

    @NotNull(message = "The password should not be empty")
    @Size(min = 5, message = "The password should have at least 5 characters")
    private String password;

    private Integer age = 0;
}
