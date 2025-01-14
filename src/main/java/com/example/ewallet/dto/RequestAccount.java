package com.example.ewallet.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

public class RequestAccount {
    private String name;
    private String email;
    private String phone;
    private String password;
}
