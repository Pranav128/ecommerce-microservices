package com.app.ecommerce.product_service.repository;

import com.app.ecommerce.product_service.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
    Page<Product> findByNameContainingIgnoreCase(String name, Pageable pageable);
    Page<Product> findByNameContainingIgnoreCaseAndCategory_Id(String name, Long categoryId, Pageable pageable);
    Page<Product> findByCategory_Id(Long categoryId,Pageable pageable);
    boolean existsByNameIgnoreCase(String name);
}
