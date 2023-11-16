package com.hunglh.backend.service;

import com.hunglh.backend.entities.OrderItem;
import com.hunglh.backend.entities.Orders;
import graphql.schema.DataFetcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface OrderService {

    Orders getOrderById(Long orderId);

    List<OrderItem> getOrderItemsByOrderId(Long orderId);
    
    Page<Orders> getAllOrders(Pageable pageable);

    Page<Orders> getUserOrders(String email, Pageable pageable);

    Orders postOrder(Orders validOrder, Map<Long, Long> productsId);

    String deleteOrder(Long orderId);

    DataFetcher<List<Orders>> getAllOrdersByQuery();

    DataFetcher<List<Orders>> getUserOrdersByEmailQuery();
}
