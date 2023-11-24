package com.hunglh.backend.service;

import com.hunglh.backend.entities.Users;

import java.util.Map;

public interface AuthService {

    Map<String, Object> login(String email, String password);

    String registerUser(Users user, String password2);

//    Users registerOauth2User(String provider, OAuth2UserInfo oAuth2UserInfo);

//    Users updateOauth2User(Users user, String provider, OAuth2UserInfo oAuth2UserInfo);

    String activateUser(String code);

    String getEmailByPasswordResetCode(String code);

    String sendPasswordResetCode(String email);

    String passwordReset(String email, String password, String password2);
}
