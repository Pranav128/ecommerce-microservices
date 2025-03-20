package com.app.ecommerce.product_service.service.specification;

import com.app.ecommerce.product_service.dto.request.CategoryRequestDto;
import com.app.ecommerce.product_service.dto.response.CategoryResponseDto;

import java.util.List;

public interface CategoryService {

    List<CategoryResponseDto> getAllCategories();

    CategoryResponseDto getCategoryById(Long id);

    CategoryResponseDto createCategory(CategoryRequestDto categoryDTO);

    CategoryResponseDto updateCategory(Long id, CategoryRequestDto categoryDTO);

    void deleteCategory(Long id);

}