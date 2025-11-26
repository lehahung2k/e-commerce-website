package com.hunglh.backend.controller;

import com.hunglh.backend.repositories.ProductRepository;
import com.hunglh.backend.service.OrderProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/load")
public class LoadTestController {

    @Autowired
    private OrderProducer orderProducer;

    @Autowired
    private ProductRepository productRepository;

    @PostMapping("/queue")
    public Map<String, String> sendToQueue(@RequestBody Map<String, Object> data) {
        orderProducer.sendOrder(data);
        return Map.of("status", "sent to queue");
    }

    @GetMapping("/db")
    public Map<String, Object> queryDB() {
        long start = System.currentTimeMillis();
        long count = productRepository.count();
        productRepository.findAll();
        long duration = System.currentTimeMillis() - start;
        return Map.of("products", count, "duration_ms", duration);
    }

    @GetMapping("/heavy-db")
    public Map<String, Object> heavyQuery() {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 10; i++) {
            productRepository.findAll();
        }
        long duration = System.currentTimeMillis() - start;
        return Map.of("queries", 10, "duration_ms", duration);
    }
}
