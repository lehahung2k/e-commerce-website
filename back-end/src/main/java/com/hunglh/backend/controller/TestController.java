package com.hunglh.backend.controller;

import com.hunglh.backend.service.OrderProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private OrderProducer orderProducer;

    @PostMapping("/order")
    public Map<String, String> createTestOrder(@RequestBody Map<String, Object> order) {
        orderProducer.sendOrder(order);
        Map<String, String> response = new HashMap<>();
        response.put("status", "Order sent to queue");
        return response;
    }
}
