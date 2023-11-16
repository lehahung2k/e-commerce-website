package com.hunglh.backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.hunglh.backend.constaints.PathContants.API;

@RestController
@RequiredArgsConstructor
@RequestMapping(API)
public class ExampleController {
    @GetMapping("")
    public String hello() {
        return "Hello World!";
    }
}
