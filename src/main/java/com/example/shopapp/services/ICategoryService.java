package com.example.shopapp.services;

import com.example.shopapp.dtos.CategoryDTO;
import com.example.shopapp.models.Category;

import java.util.List;

public interface ICategoryService {
    Category createcategory(CategoryDTO categoryDTO);

    Category getCategoryById(Long id);

    List<Category> getAllcategory();

    Category updateCategory(Long categoryId,CategoryDTO category);

    void deleteCategory(Long categoryId);
}
