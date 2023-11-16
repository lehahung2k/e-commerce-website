package com.hunglh.backend.repository;

import com.hunglh.backend.entities.Orders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Long> {

    List<Orders> findAllByOrderByIdAsc();

    Page<Orders> findAllByOrderByIdAsc(Pageable pageable);

    List<Orders> findOrderByEmail(String email);

    Page<Orders> findOrderByEmail(String email, Pageable pageable);
}
