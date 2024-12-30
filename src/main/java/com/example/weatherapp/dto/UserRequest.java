package com.example.weatherapp.dto;

import lombok.Data;

@Data
public class UserRequest {
    private String username;
    private String postalCode;
}
