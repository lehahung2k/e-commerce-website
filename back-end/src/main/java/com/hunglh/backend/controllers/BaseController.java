package com.hunglh.backend.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("")
public class BaseController {
    @GetMapping("")
    public String HelloWorld() {
        return "Hello, this our web API";
    }
}
