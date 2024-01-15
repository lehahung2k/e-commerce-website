package com.hunglh.backend.services.impl;

import com.hunglh.backend.entities.OrderMain;
import com.hunglh.backend.entities.ProductInOrder;
import com.hunglh.backend.entities.Products;
import com.hunglh.backend.enums.OrderStatusEnum;
import com.hunglh.backend.repositories.OrderRepository;
import com.hunglh.backend.repositories.ProductInOrderRepository;
import com.hunglh.backend.repositories.ProductRepository;
import com.hunglh.backend.repositories.UserRepository;
import com.hunglh.backend.services.OrderService;
import com.hunglh.backend.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final ProductService productService;
    private final ProductInOrderRepository productInOrderRepository;

    @Override
    public ResponseEntity<Object> findAll(Pageable pageable) {
        Page<OrderMain> order =  orderRepository.findAllByOrderByOrderStatusAscCreateTimeDesc(pageable);
        return ResponseEntity.status(200).body(Map.of(
                "orders", order,
                "totalPages", order.getTotalPages(),
                "totalElements", order.getTotalElements()
        ));
    }

    @Override
    public Page<OrderMain> findByStatus(Integer status, Pageable pageable) {
        return orderRepository.findAllByOrderStatusOrderByCreateTimeDesc(status, pageable);
    }

    @Override
    public ResponseEntity<Object> findByBuyerEmail(String email, Pageable pageable) {
        Page<OrderMain> order = orderRepository.findAllByBuyerEmailOrderByOrderStatusAscCreateTimeDesc(email, pageable);
        return ResponseEntity.status(200).body(Map.of(
                "orders", order,
                "totalPages", order.getTotalPages(),
                "totalElements", order.getTotalElements()
        ));
    }

    @Override
    public Page<OrderMain> findByBuyerPhone(String phone, Pageable pageable) {
        return orderRepository.findAllByBuyerPhoneOrderByOrderStatusAscCreateTimeDesc(phone, pageable);
    }

    @Override
    public OrderMain findOne(Long orderId) {
        try {
            return orderRepository.findByOrderId(orderId);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public OrderMain finish(Long orderId) {
        OrderMain orderMain = findOne(orderId);
        if(!orderMain.getOrderStatus().equals(OrderStatusEnum.IN_PROGRESS.getCode())) {
            throw new RuntimeException("Order Status Error");
        }

        orderMain.setOrderStatus(OrderStatusEnum.FINISHED.getCode());
        orderRepository.save(orderMain);
        return orderRepository.findByOrderId(orderId);
    }

    @Override
    @Transactional
    public OrderMain deliver(Long orderId) {
        OrderMain orderMain = findOne(orderId);
        orderMain.setOrderStatus(OrderStatusEnum.IN_PROGRESS.getCode());
        orderRepository.save(orderMain);
        return orderRepository.findByOrderId(orderId);
    }

    @Override
    @Transactional
    public OrderMain cancel(Long orderId) {
        OrderMain orderMain = findOne(orderId);
        if(!orderMain.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
            throw new RuntimeException("Order Status Error");
        }

        orderMain.setOrderStatus(OrderStatusEnum.CANCELED.getCode());
        orderRepository.save(orderMain);

        // Restore Stock
        Iterable<ProductInOrder> products = orderMain.getProducts();
        for(ProductInOrder productInOrder : products) {
            Products product = productRepository.findByProductId(productInOrder.getProductId());
            if(product != null) {
                productService.increaseStock(productInOrder.getProductId(), productInOrder.getCount());
            }
        }
        return orderRepository.findByOrderId(orderId);
    }
}
