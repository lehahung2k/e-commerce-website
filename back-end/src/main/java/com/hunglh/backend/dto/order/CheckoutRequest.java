package com.hunglh.backend.dto.order;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CheckoutRequest {

    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    @NotBlank(message = "Phone is required")
    private String phone;

    @NotBlank(message = "Address is required")
    private String address;

    private String address2;

    @NotBlank(message = "City is required")
    private String city;

    @NotBlank(message = "Country is required")
    private String country;

    private String zip;

    /** COD (cash on delivery) or ONLINE */
    private String paymentMethod = "COD";
}
