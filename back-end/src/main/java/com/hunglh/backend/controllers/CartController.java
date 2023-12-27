package com.hunglh.backend.controllers;

import com.hunglh.backend.dto.product.NewProduct;
import com.hunglh.backend.entities.Cart;
import com.hunglh.backend.entities.ProductInOrder;
import com.hunglh.backend.entities.Users;
import com.hunglh.backend.repositories.ProductInOrderRepository;
import com.hunglh.backend.services.CartService;
import com.hunglh.backend.services.ProductInOrderService;
import com.hunglh.backend.services.ProductService;
import com.hunglh.backend.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Collection;
import java.util.Collections;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {
    private final CartService cartService;
    private final UserService userService;
    private final ProductService productService;
    private final ProductInOrderService productInOrderService;
    private final ProductInOrderRepository productInOrderRepository;

    @PostMapping("")
    public ResponseEntity<Cart> mergeCart(@RequestBody Collection<ProductInOrder> productInOrders, Principal principal) {
        Users user = userService.getUserByEmail(principal.getName());
        try {
            cartService.mergeLocalCart(productInOrders, user);
        } catch (Exception e) {
            ResponseEntity.badRequest().body("Merge Cart Failed");
        }
        return ResponseEntity.ok(cartService.getCart(user));
    }

    @GetMapping("")
    public Cart getCart(Principal principal) {
        Users user = userService.getUserByEmail(principal.getName());
        return cartService.getCart(user);
    }

    @PostMapping("/add")
    public boolean addToCart(@RequestBody NewProduct newProduct, Principal principal) {
        var productInfo = productService.findOne(newProduct.getProductId());
        try {
            mergeCart(Collections.singleton(new ProductInOrder(productInfo, newProduct.getQuantityInStock())), principal);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @PutMapping("/{itemId}")
    public ProductInOrder modifyItem(@PathVariable("itemId") Long itemId, @RequestBody Integer quantity, Principal principal) {
        Users user = userService.getUserByEmail(principal.getName());
        productInOrderService.update(itemId, quantity, user);
        return productInOrderService.findOne(itemId, user);
    }

    @DeleteMapping("/{itemId}")
    public void deleteItem(@PathVariable("itemId") Long itemId, Principal principal) {
        Users user = userService.getUserByEmail(principal.getName());
        cartService.delete(itemId, user);
         // flush memory into DB
    }

    @PostMapping("/checkout")
    public ResponseEntity<Object> checkout(Principal principal) {
        Users user = userService.getUserByEmail(principal.getName());// Email as username
        cartService.checkout(user);
        return ResponseEntity.ok(null);
    }

}
