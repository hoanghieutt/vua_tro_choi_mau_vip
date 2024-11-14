package com.example.shopapp.services;

import com.example.shopapp.Responses.ProductResponse;
import com.example.shopapp.dtos.ProductDTO;
import com.example.shopapp.dtos.ProductImageDTO;
import com.example.shopapp.models.Product;
import com.example.shopapp.models.ProductImages;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public interface IProductService {
    public Product createProduct(ProductDTO productDTO) throws  Exception;

    Product getProductById(long id) throws Exception;

    Product getProductByIdCustom(long id) throws Exception;

    Page<ProductResponse> getAllProducts(PageRequest pageRequest);

    Product updateProduct(long id,ProductDTO productDTO ) throws Exception;

    void deleteProduct(long id);

    boolean existsByName(String name);

    ProductImages createProductImage(Long productId, ProductImageDTO productImageDTO) throws Exception;

    List<Product> searchProductByName(String name);

    void kafkaCreate(ProductDTO product);

    void rabbitCreate(ProductDTO product) throws Exception;
}
