package com.hunglh.backend.dto.user;

import lombok.Data;

@Data
public class UserResponse {
    private String firstName;
    private String lastName;
    private String email;
    private String address;
    private String city;
    private String country;
    private String postalCode;
    private String phoneNumber;
    private boolean active;
}
