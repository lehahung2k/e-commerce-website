package com.hunglh.backend.controllers;

import com.hunglh.backend.entities.OrderMain;
import com.hunglh.backend.entities.ProductInOrder;
import com.hunglh.backend.services.OrderService;
import com.hunglh.backend.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@CrossOrigin
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final UserService userService;

    @GetMapping("/order")
    public ResponseEntity<Object> orderList(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                     @RequestParam(value = "size", defaultValue = "10") Integer size,
                                     Authentication authentication) {
        PageRequest request = PageRequest.of(page - 1, size);
        ResponseEntity<Object> orderPage;
        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_CUSTOMER"))) {
            orderPage = orderService.findByBuyerEmail(authentication.getName(), request);
        } else {
            orderPage = orderService.findAll(request);
        }
        return orderPage;
    }

    @PatchMapping("/order/cancel/{orderId}")
    public ResponseEntity<OrderMain> cancel(@PathVariable("orderId") Long orderId, Authentication authentication) {
        OrderMain orderMain = orderService.findOne(orderId);
        if (!authentication.getName().equals(orderMain.getBuyerEmail()) && authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_CUSTOMER"))) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(orderService.cancel(orderId));
    }

    @PatchMapping("/order/finish/{orderId}")
    public ResponseEntity<OrderMain> finish(@PathVariable("orderId") Long orderId, Authentication authentication) {
        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_CUSTOMER"))) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(orderService.finish(orderId));
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity show(@PathVariable("orderId") Long orderId, Authentication authentication) {
        boolean isCustomer = authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_CUSTOMER"));
        OrderMain orderMain = orderService.findOne(orderId);
        if (isCustomer && !authentication.getName().equals(orderMain.getBuyerEmail())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Collection<ProductInOrder> items = orderMain.getProducts();
        System.out.println(items);
        return ResponseEntity.ok(orderMain);
    }
}
