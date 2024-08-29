package com.example.shopapp.services.impl;

import com.example.shopapp.Responses.ProductResponse;
import com.example.shopapp.dtos.ProductDTO;
import com.example.shopapp.dtos.ProductImageDTO;
import com.example.shopapp.exceptions.DataNotFoundException;
import com.example.shopapp.exceptions.InvalidParameterException;
import com.example.shopapp.models.Category;
import com.example.shopapp.models.Product;
import com.example.shopapp.models.ProductImages;
import com.example.shopapp.repositories.CategoryRepository;
import com.example.shopapp.repositories.ProductImageRepository;
import com.example.shopapp.repositories.ProductRepository;
import com.example.shopapp.services.IProductService;
import com.example.shopapp.services.RabbitmqService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {

    @Value("${application.rabbitmq.queue.create_product}")
    private String queueCreateProduct;

    @Value("${application.kafka.topic.create_product}")
    private String topicCreateProduct;

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductImageRepository productImageRepository;

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final RabbitmqService rabbitmqService;
    @Override
    public Product createProduct(ProductDTO productDTO) throws Exception {
        Category existingcategory = categoryRepository.findById(productDTO.getCategoryId())
                .orElseThrow(()-> new DataNotFoundException("cannot find category with id " + productDTO.getCategoryId()));
        Product newproduct = Product.builder()
                .name(productDTO.getName())
                .price(productDTO.getPrice())
                .thumbnail(productDTO.getThumbnail())
                .category(existingcategory)
                .build();
        return productRepository.save(newproduct);
    }

    @Override
    public Product getProductById(long productId) throws Exception {
        return productRepository.findById(productId)
                .orElseThrow(()-> new DataNotFoundException("cannot find product with id = "+ productId) );
    }

    @Override
    public Product getProductByIdCustom(long id) throws Exception {
        return null;
    }

    @Override
    public Page<ProductResponse> getAllProducts(PageRequest pageRequest) {
        // lấy danh sách sản phẩm theo trang (page) và giới hạn (limit)
        return productRepository.findAll(pageRequest).map(product -> {
               ProductResponse productResponse = ProductResponse.builder()
                    .name(product.getName())
                    .price(product.getPrice())
                    .thumbnail(product.getThumbnail())
                    .description(product.getDescription())
                    .categoryId(product.getCategory().getId())
                    .build();
               productResponse.setCreatedAt(product.getCreatedAt());
               productResponse.setUpdatedAt(product.getUpdatedAt());
               return productResponse;
        });
    }

    @Override
    public Product updateProduct(long id, ProductDTO productDTO) throws Exception {
        Product existingProduct = getProductById(id);
        if (existingProduct != null){
            // copy các thuộc tính DTO -> Product
            // có thể sử dụng ModelMapper
            Category existingcategory = categoryRepository.findById(productDTO.getCategoryId())
                    .orElseThrow(()-> new DataNotFoundException("cannot find with id :"+productDTO.getCategoryId()));
            existingProduct.setName(productDTO.getName());
            existingProduct.setCategory(existingcategory);
            existingProduct.setPrice(productDTO.getPrice());
            existingProduct.setDescription(productDTO.getDescription());
            existingProduct.setThumbnail(productDTO.getThumbnail());
            return productRepository.save(existingProduct);
        }
        return null;
    }


    @Override
    public void deleteProduct(long id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
//        if (optionalProduct.isPresent()){
//            productRepository.delete(optionalProduct.get());
//        }
        optionalProduct.ifPresent(productRepository::delete);
    }
    @Override
    public boolean existsByName(String name){
        return productRepository.existsByName(name);
    }
    @Override
    public ProductImages createProductImage(
            Long productId,
            ProductImageDTO productImageDTO) throws Exception {
        Product existinPproduct = productRepository
                .findById(productId)
                .orElseThrow(()-> new DataNotFoundException(
                        "cannot find category :"+productImageDTO.getProductId()));

        ProductImages newproductImages = ProductImages.builder()
                .product(existinPproduct)
                .imageUrl(productImageDTO.getImageUrl())
                .build();
            // ko insert quá 5 ảnh trong 1 SẢN phẩm
        int size =productImageRepository.findByProductId(productId).size();
        if (size >= ProductImages.MAXIMUM_IMAGES_PER_PRODUCT){
            throw new InvalidParameterException("numer of image must be <= "+ProductImages.MAXIMUM_IMAGES_PER_PRODUCT);
        }
            return productImageRepository.save(newproductImages);
    }

    @Override
    public List<Product> searchProductByName(String name) {
        return productRepository.searchProductByName(name);
    }

    @Override
    public void kafkaCreate(ProductDTO product) {
        kafkaTemplate.send(topicCreateProduct,product);
    }

    @Override
    public void rabbitCreate(ProductDTO product) throws Exception {
        if (product.equals("")){
            throw new InvalidParameterException("productDTO null");
        }
        rabbitmqService.push(queueCreateProduct,product);
    }
}
