package com.hunglh.backend.services;

import com.hunglh.backend.entities.ProductInOrder;
import com.hunglh.backend.entities.Users;

public interface ProductInOrderService {
    void update(Long itemId, Integer quantity, Users user);
    ProductInOrder findOne(Long itemId, Users user);
}
