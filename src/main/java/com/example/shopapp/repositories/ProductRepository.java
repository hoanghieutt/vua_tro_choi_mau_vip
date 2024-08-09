package com.example.shopapp.repositories;

import com.example.shopapp.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long> {
    Boolean existsByName(String name);

    Page<Product> findAll(Pageable pageable);// phân trang

    @Query("select p from Product p where p.name like %:name%")
    List<Product> searchProductByName(@Param("name") String name);
}
