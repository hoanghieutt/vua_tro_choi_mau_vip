package com.example.shopapp.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class OrderDetailDTO {
    @JsonProperty("order_id")
    @Min(value = 1,message = "order's id must be > 0")
    private Long orderId;

    @JsonProperty("product_id")
    @Min(value = 1,message = "product_id's id must be > 0")
    private Long productId;

    @Min(value = 0,message = "price's id must be >= 0")
    private Long price;

    @JsonProperty("number_of_products")
    @Min(value = 1,message = "number_of_products's id must be >= 0")
    private int numberOfProducts;


    @JsonProperty("total_money")
    @Min(value = 0,message = "total_money's id must be >= 0")
    private int totalMoney;

    private String color;
}
