package com.dawid.repositories;

import com.dawid.domain.Category;
import com.dawid.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;


public interface ProductRepository extends JpaRepository<Product, Long> {

    Set<Product> findAllByDiscountTrue();
    Set<Product> findAllByRecentTrue();
    Set<Product> findAllByCategoriesContains(Category category);
}
