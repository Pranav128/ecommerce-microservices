package com.app.ecommerce.product_service.controller;

import com.app.ecommerce.product_service.dto.request.ProductRequestDto;
import com.app.ecommerce.product_service.dto.response.ProductResponseDto;
import com.app.ecommerce.product_service.service.specification.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tag( name = "Product", description = "Product API")
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    @GetMapping
    @Operation(summary = "Get all products", description = "Returns a list of all products")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Products found"),
            @ApiResponse(responseCode = "404", description = "Products not found")
    })
    public ResponseEntity<Page<ProductResponseDto>> getAllProducts(
                                                    @RequestParam(defaultValue = "0", required = false) int page,
                                                    @RequestParam(defaultValue = "30", required = false) int size,
                                                    @RequestParam(defaultValue = "id,asc", required = false) String sort) {
        return ResponseEntity.ok(productService.getAllProducts(page, size, sort));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get product by id", description = "Returns a product by id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Product found"),
                    @ApiResponse(responseCode = "404", description = "Product not found")
            }
    )
    public ResponseEntity<ProductResponseDto> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @PostMapping
    @Operation(summary = "Create a new product", description = "Returns the created product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Product created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    public ResponseEntity<ProductResponseDto> createProduct(@RequestBody @Valid ProductRequestDto productDTO,@RequestHeader("X-User-Role") String role) {
        if(!role.contains("ROLE_ADMIN")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build(); // 403 Forbidden
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.createProduct(productDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a product", description = "Deletes a product by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Product deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Product not found"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    public ResponseEntity<?> deleteProduct(@PathVariable Long id, @RequestHeader("X-User-Role") String role) {
        // Check if the user has the ROLE_ADMIN role
        if (!role.contains("ROLE_ADMIN")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build(); // 403 Forbidden
        }
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a product", description = "Returns the updated product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product updated successfully"),
            @ApiResponse(responseCode = "404", description = "Product not found"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    public ResponseEntity<ProductResponseDto> updateProduct(@PathVariable Long id, @RequestBody @Valid ProductRequestDto productDTO, @RequestHeader("X-User-Role") String role) {
        if (!role.contains("ROLE_ADMIN")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build(); // 403 Forbidden
        }
        return ResponseEntity.ok(productService.updateProduct(id, productDTO));
    }

    @GetMapping("/search")
    @Operation(summary = "Search for products", description = "Returns a list of products that match the search criteria")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Products found"),
            @ApiResponse(responseCode = "404", description = "Products not found")
    })
    public ResponseEntity<Page<ProductResponseDto>> searchProducts(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Long category,
            @RequestParam(defaultValue = "0", required = false) int page,
            @RequestParam(defaultValue = "30", required = false) int size,
            @RequestParam(defaultValue = "id,asc", required = false) String sort) {
        return ResponseEntity.ok(productService.searchProducts(name, category, page, size, sort));
    }


    @GetMapping("/is-name-available")
    @Operation(summary = "Check if product name is available", description = "Returns true if the name is available")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Name is available"),
            @ApiResponse(responseCode = "404", description = "Name is not available")
    })
    public ResponseEntity<Boolean> isNameAvailable(@RequestParam String name) {
        return ResponseEntity.ok(productService.existsByNameIgnoreCase(name));
    }
}