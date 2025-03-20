package com.app.ecommerce.product_service.service.implementation;

import com.app.ecommerce.product_service.dto.request.CategoryRequestDto;
import com.app.ecommerce.product_service.dto.response.CategoryResponseDto;
import com.app.ecommerce.product_service.entity.Category;
import com.app.ecommerce.product_service.exception.ProductException;
import com.app.ecommerce.product_service.repository.CategoryRepository;
import com.app.ecommerce.product_service.service.specification.CategoryService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<CategoryResponseDto> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(category -> modelMapper.map(category, CategoryResponseDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public CategoryResponseDto getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .map(category -> modelMapper.map(category, CategoryResponseDto.class))
                .orElseThrow(() -> new ProductException("Category not found"));
    }

    @Override
    public CategoryResponseDto createCategory(CategoryRequestDto categoryDTO) {
        Category category = modelMapper.map(categoryDTO, Category.class);
        categoryRepository.save(category);
        return modelMapper.map(category, CategoryResponseDto.class);
    }

    @Override
    public CategoryResponseDto updateCategory(Long id, CategoryRequestDto categoryDTO) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ProductException("Category not found"));
        category.setName(categoryDTO.getName());
        categoryRepository.save(category);
        return modelMapper.map(category, CategoryResponseDto.class);
    }

    @Override
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }
}