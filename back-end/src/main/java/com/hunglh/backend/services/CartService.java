package com.hunglh.backend.services;

import com.hunglh.backend.entities.Cart;
import com.hunglh.backend.entities.ProductInOrder;
import com.hunglh.backend.entities.Users;

import java.util.Collection;

public interface CartService {
    Cart getCart(Users user);

    void mergeLocalCart(Collection<ProductInOrder> productInOrders, Users user);

    void delete(Long itemId, Users user);

    void checkout(Users user);
}
