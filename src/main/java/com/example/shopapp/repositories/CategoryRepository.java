package com.example.shopapp.repositories;

import com.example.shopapp.models.Category;
import com.example.shopapp.models.OrderDtail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category,Long> {

}
