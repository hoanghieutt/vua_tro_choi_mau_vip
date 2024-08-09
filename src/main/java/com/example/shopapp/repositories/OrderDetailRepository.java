package com.example.shopapp.repositories;

import com.example.shopapp.models.OrderDtail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailRepository extends JpaRepository<OrderDtail,Long> {
}
