package com.hunglh.backend.constaints;

public class PathContants {
    public static final String API = "/api/v1";
    public static final String ORDER = "/order";
    public static final String ORDERS = "/orders";
    public static final String PRODUCTS = "/products";
    public static final String USER = "/user";
    public static final String CART = "/cart";
    public static final String API_USERS = API + "/users";
    public static final String API_ADMIN = API + "/admin";

    // auth path
    public static final String AUTH = API + "/auth";
    public static final String LOGIN = AUTH + "/login";
    public static final String FORGOT_EMAIL = AUTH + "/forgot-email";
    public static final String RESET_CODE = AUTH + "/reset-code/{code}";
    public static final String RESET = AUTH + "/reset";
    public static final String EDIT_PASSWORD = AUTH + "/edit-password";

    // register path
    public static final String REGISTER = API + "/register";
    public static final String ACTIVATE_CODE = REGISTER + "/activate/{code}";

    public static final String GRAPHQL = "/graphql";
}
