package com.example.shopapp.dtos;

import lombok.*;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDTO {

    private String name;

    private Float price;

    private String thumbnail;

    private String description;

    private Long categoryId;

}
