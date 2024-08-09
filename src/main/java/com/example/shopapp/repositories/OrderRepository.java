package com.example.shopapp.repositories;

import com.example.shopapp.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Long> {
    // tìm kiếm tài khoản của 1 user nào đó
//    List<Order> findByUserID(Long userId);
}
