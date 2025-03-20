package com.app.ecommerce.product_service.service.specification;

import com.app.ecommerce.product_service.dto.request.ProductRequestDto;
import com.app.ecommerce.product_service.dto.response.ProductResponseDto;
import org.springframework.data.domain.Page;

public interface ProductService {
    ProductResponseDto createProduct(ProductRequestDto productRequest);
    Page<ProductResponseDto> getAllProducts(int page, int size, String sort);
    ProductResponseDto getProductById(Long id);
    ProductResponseDto updateProduct(Long id, ProductRequestDto productRequest);
    void deleteProduct(Long id);
    Page<ProductResponseDto> searchProducts(String name, Long category, int page, int size, String sort);
    boolean existsByNameIgnoreCase(String name);
}
