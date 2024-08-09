package com.example.shopapp.repositories;

import com.example.shopapp.models.ProductImages;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductImageRepository extends JpaRepository<ProductImages,Long> {
    List<ProductImages> findByProductId(Long productid);
}
