package com.app.ecommerce.product_service.service.implementation;

import com.app.ecommerce.product_service.dto.request.ProductRequestDto;
import com.app.ecommerce.product_service.dto.response.ProductResponseDto;
import com.app.ecommerce.product_service.entity.Category;
import com.app.ecommerce.product_service.entity.Product;
import com.app.ecommerce.product_service.exception.ProductException;
import com.app.ecommerce.product_service.repository.CategoryRepository;
import com.app.ecommerce.product_service.repository.ProductRepository;
import com.app.ecommerce.product_service.service.specification.ProductService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ModelMapper  modelMapper;
    private final CategoryRepository categoryRepository;

    @Override
    public ProductResponseDto createProduct(ProductRequestDto productRequest) {
        Product product = modelMapper.map(productRequest, Product.class);
        Category category=categoryRepository.findById(productRequest.getCategoryId())
                .orElseThrow(() -> new ProductException("Category not found"));
        product.setCategory(category);
        productRepository.save(product);
        return modelMapper.map(product, ProductResponseDto.class);
    }

    @Override
    public Page<ProductResponseDto> getAllProducts(int page, int size, String sort) {

        // Create a Pageable object
        Pageable pageable = createPageable(page, size, sort);

        // Fetch tasks with pagination and sorting
        Page<Product> products = productRepository.findAll(pageable);

        if(products.isEmpty()) throw new ProductException("Products not found");

        return products.map(this::mapToProductResponseDto);
    }

    @Override
    public ProductResponseDto getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductException("Product not found"));
        return modelMapper.map(product, ProductResponseDto.class);
    }

    @Override
    public ProductResponseDto updateProduct(Long id, ProductRequestDto productRequest) {
        // Fetch the existing product
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductException("Product not found"));
        product.setName(productRequest.getName());
        product.setDescription(productRequest.getDescription());
        product.setPrice(productRequest.getPrice());
        product.setStock(productRequest.getStock());

        // Update the category
        Category category=categoryRepository.findById(productRequest.getCategoryId())
                .orElseThrow(() -> new ProductException("Category not found"));
        product.setCategory(category);

        // Save the updated product
        productRepository.save(product);
        return modelMapper.map(product, ProductResponseDto.class);
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public Page<ProductResponseDto> searchProducts(String name, Long category, int page, int size, String sort) {

        // Create a Pageable object
        Pageable pageable = createPageable(page, size, sort);
        Page<Product> products;

        if (name != null && category != null) {
            products = productRepository.findByNameContainingIgnoreCaseAndCategory_Id(name, category, pageable);
        } else if (name != null) {
            products = productRepository.findByNameContainingIgnoreCase(name, pageable);
        } else if (category != null) {
            products = productRepository.findByCategory_Id(category, pageable);
        } else {
            products = productRepository.findAll(pageable); // Return all products if no search criteria
        }
        return products.map(this::mapToProductResponseDto);
    }

    @Override
    public boolean existsByNameIgnoreCase(String name) {
        return productRepository.existsByNameIgnoreCase(name);
    }

    private ProductResponseDto mapToProductResponseDto(Product product) {
        return modelMapper.map(product, ProductResponseDto.class);
    }

    private Pageable createPageable(int page, int size, String sort) {
        String[] sortParams = sort.split(",");
        Sort.Direction direction = Sort.Direction.fromString(sortParams[1]);
        Sort sortObj = Sort.by(direction, sortParams[0]);
        return PageRequest.of(page, size, sortObj);
    }
}
