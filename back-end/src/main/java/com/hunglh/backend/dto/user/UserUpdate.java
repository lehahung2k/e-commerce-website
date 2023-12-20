package com.hunglh.backend.dto.user;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UserUpdate {
    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotBlank
    private String address;

    @NotBlank
    private String city;

    @NotBlank
    private String country;

    @NotBlank
    private String postalCode;

    @NotBlank
    private String phoneNumber;
}
