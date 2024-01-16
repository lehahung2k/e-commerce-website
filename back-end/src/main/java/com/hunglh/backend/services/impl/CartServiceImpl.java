package com.hunglh.backend.services.impl;

import com.hunglh.backend.entities.Cart;
import com.hunglh.backend.entities.OrderMain;
import com.hunglh.backend.entities.ProductInOrder;
import com.hunglh.backend.entities.Users;
import com.hunglh.backend.repositories.CartRepository;
import com.hunglh.backend.repositories.OrderRepository;
import com.hunglh.backend.repositories.ProductInOrderRepository;
import com.hunglh.backend.repositories.UserRepository;
import com.hunglh.backend.services.CartService;
import com.hunglh.backend.services.ProductService;
import com.hunglh.backend.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final ProductService productService;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductInOrderRepository productInOrderRepository;
    private final CartRepository cartRepository;
    private final UserService userService;

    @Override
    public Cart getCart(Users user) {
        return user.getCart();
    }

    @Override
    @Transactional
    public void mergeLocalCart(Collection<ProductInOrder> productInOrders, Users user) {
        try {
            Cart finalCart = user.getCart();
            productInOrders.forEach(productInOrder -> {
                Set<ProductInOrder> set = finalCart.getProducts();
                Optional<ProductInOrder> old = set.stream().filter(e -> e.getProductId().equals(productInOrder.getProductId())).findFirst();
                ProductInOrder prod;
                if (old.isPresent()) {
                    prod = old.get();
                    prod.setCount(productInOrder.getCount() + prod.getCount());
                } else {
                    prod = productInOrder;
                    prod.setCart(finalCart);
                    finalCart.getProducts().add(prod);
                }
                productInOrderRepository.save(prod);
            });
            cartRepository.save(finalCart);
        } catch (Exception e) {
            ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Override
    @Transactional
    public void delete(Long itemId, Users user) {
        if(itemId == null || user == null) {
            throw new RuntimeException("Invalid input");
        }

        var op = user.getCart().getProducts().stream().filter(e -> itemId.equals(e.getProductId())).findFirst();
        op.ifPresent(productInOrder -> {
            productInOrder.setCart(null);
            productInOrderRepository.deleteById(productInOrder.getId());
        });
    }

    @Override
    @Transactional
    public void checkout(Users user) {
        // Creat an order
        OrderMain order = new OrderMain(user);
        orderRepository.save(order);

        // clear cart's foreign key & set order's foreign key& decrease stock
        user.getCart().getProducts().forEach(productInOrder -> {
            productInOrder.setCart(null);
            productInOrder.setOrderMain(order);
            productService.decreaseStock(productInOrder.getProductId(), productInOrder.getCount());
            productInOrderRepository.save(productInOrder);
        });

    }
}
