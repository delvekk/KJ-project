package com.dawid.repositories;

import com.dawid.domain.Product;
import com.dawid.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    Set<Review> findByProduct(Product l);
}
