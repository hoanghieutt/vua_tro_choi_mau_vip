package com.example.shopapp.controller;


import com.example.shopapp.Responses.ProductListResponse;
import com.example.shopapp.Responses.ProductResponse;
import com.example.shopapp.dtos.ProductDTO;
import com.example.shopapp.dtos.ProductImageDTO;
import com.example.shopapp.http_reponses.SuccessResponse;
import com.example.shopapp.models.Product;
import com.example.shopapp.models.ProductImages;
import com.example.shopapp.services.IProductService;
import com.github.javafaker.Faker;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("${api.prefix}/products")
public class ProductController {
    private final IProductService productService;

    @PostMapping("kafka")
    public void createtest( @Valid @RequestBody ProductDTO request) throws Exception {
        productService.kafkaCreate(request);
    }
    @PostMapping("rabbitmq")
    public void createQueue(@Valid @RequestBody ProductDTO requet) throws Exception {
        productService.rabbitCreate(requet);
    }


    @PostMapping("")
    //POST http://localhost:8088/v1/api/products
    public ResponseEntity<?> createProduct(
            @Valid @RequestBody ProductDTO productDTO,
            BindingResult result
    ) {
        try {
            if (result.hasErrors()) {
                List<String> errorMessages = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessages);
            }
            Product newProduct = productService.createProduct(productDTO);
            log.info("This is an info message");
//            return ResponseEntity.badRequest().body(newProduct);
            return ResponseEntity.ok().body(new SuccessResponse<>(newProduct));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping(value = "uploads/{id}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    //POST http://localhost:8088/v1/api/products
    public ResponseEntity<?> uploadImages(
            @PathVariable("id") Long productId,
            @ModelAttribute("files") List<MultipartFile> files
    ) {
        try {
            Product existingProduct = productService.getProductById(productId);
            files = files == null ? new ArrayList<MultipartFile>() : files;
            if (files.size() > ProductImages.MAXIMUM_IMAGES_PER_PRODUCT) {
                return ResponseEntity.badRequest().body("you can only uploat maximum 5 images");

            }
            List<ProductImages> productImages = new ArrayList<>();

            for (MultipartFile file : files) {
                if (file.getSize() == 0) {
                    continue;
                }
                // Kiểm tra kích thước file và định dạng
                if (file.getSize() > 10 * 1024 * 1024) { // Kích thước > 10MB
                    return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE)
                            .body("File is too large! Maximum size is 10MB");
                }
                String contentType = file.getContentType();
                if (contentType == null || !contentType.startsWith("image/")) {
                    return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                            .body("File must be an image");
                }
                // Lưu file và cập nhật thumbnail trong DTO
                String filename = storeFile(file); // Thay thế hàm này với code của bạn để lưu file
                //lưu vào đối tượng product trong DB
                ProductImages productImage = productService.createProductImage(
                        existingProduct.getId(),
                        ProductImageDTO.builder()
                                .imageUrl(filename)
                                .build()
                );
                productImages.add(productImage);
            }
            return ResponseEntity.ok().body(productImages);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    private String storeFile(MultipartFile file) throws IOException {
        if (isImageFile(file) || file.getOriginalFilename() != null) {
            throw new IOException("Invalid image format");
        }
        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        // Thêm UUID vào trước tên file để đảm bảo tên file là duy nhất
        String uniqueFilename = UUID.randomUUID().toString() + "_" + filename;
        // Đường dẫn đến thư mục mà bạn muốn lưu file
        java.nio.file.Path uploadDir = Paths.get("uploads");
        // Kiểm tra và tạo thư mục nếu nó không tồn tại
        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }
        // Đường dẫn đầy đủ đến file
        java.nio.file.Path destination = Paths.get(uploadDir.toString(), uniqueFilename);
        // Sao chép file vào thư mục đích
        Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
        return uniqueFilename;
    }

    private boolean isImageFile(MultipartFile file) {
        String contentType = file.getContentType();
        return contentType != null && contentType.startsWith("image/");
    }

    @GetMapping("")
    public ResponseEntity<ProductListResponse> getProducts(
            @RequestParam("page") int page,
            @RequestParam("limit") int limit
    ) {
        // tạo pageable từ thông tin trang và giới hạn
        PageRequest pageRequest = PageRequest.of(
                page, limit,
                Sort.by("createdAt").descending());
        Page<ProductResponse> productPage = productService.getAllProducts(pageRequest);
        // lấy tổng số trang
        log.info("productPage: {}", productPage);
        int totalpages = productPage.getTotalPages();
        List<ProductResponse> products = productPage.getContent();
        return ResponseEntity.ok(ProductListResponse.builder()
                .product(products)
                .totalPage(totalpages)
                .build()
        );
    }

    //http://localhost:8088/api/v1/products/6
    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(
            @PathVariable("id") long productId
    ) {
        try {
            Product productnew = productService.getProductById(productId);
            return ResponseEntity.ok(productnew);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable long id) {
        return ResponseEntity.ok(String.format("Product with id = %d deleted successfully", id));
    }

    @GetMapping(value = "search")
    public ResponseEntity<?> searchProductByName(@RequestParam(defaultValue = "", value = "keyword") String keyword) {
        if (keyword.isEmpty()) {
            return ResponseEntity.ok(new SuccessResponse<>("nothing"));
        }
        List<Product> newproduct = productService.searchProductByName(keyword);
        return ResponseEntity.ok().body(newproduct);
    }

    @PostMapping("/generateFakeProducts")
    public ResponseEntity<?> generateFakeProducts() {
        Faker faker = new Faker();
        for (int i = 0; i < 1_000_000; i++) {
            String productname = faker.commerce().productName();
            if (productService.existsByName(productname)) {
                continue;
            }
            ;
            ProductDTO productDTO = ProductDTO.builder()
                    .name(productname)
                    .price((float) faker.number().numberBetween(10, 9000000))
                    .description(faker.lorem().sentence())
                    .thumbnail("")
                    .categoryId((long) faker.number().numberBetween(1, 3))
                    .build();
            try {
                productService.createProduct(productDTO);
            } catch (Exception e) {
                return ResponseEntity.ok(new SuccessResponse<>("đã succsesssfully"));
            }
        }
        return ResponseEntity.ok().body(new SuccessResponse<>("hong có cái j cả"));
    }

    @PutMapping("{id}")
    public ResponseEntity<?> updateProduct(
            @PathVariable Long id,
            @RequestBody ProductDTO productDTO

    ) {
        try {
            Product updateProduct = productService.updateProduct(id, productDTO);
            return ResponseEntity.ok(updateProduct);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new SuccessResponse<>(e.getMessage()));
        }
    }
}
