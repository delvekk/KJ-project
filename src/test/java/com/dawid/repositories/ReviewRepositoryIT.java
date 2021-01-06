package com.dawid.repositories;

import com.dawid.domain.Product;
import com.dawid.domain.Review;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Set;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ReviewRepositoryIT {

    @Autowired
    ReviewRepository reviewRepository;

    @Test
    public void findByProductTest(){
        Product product = new Product();
        product.setId(1L);

        Product product1 = new Product();
        product1.setId(2L);

        Review review = new Review();
        review.setId(1L);
        review.setProduct(product);
        review.setProductId(product.getId());
        reviewRepository.save(review);

        review = new Review();
        review.setId(2L);
        review.setProduct(product);
        review.setProductId(product.getId());
        reviewRepository.save(review);

        review = new Review();
        review.setId(3L);
        review.setProduct(product1);
        review.setProductId(product1.getId());
        reviewRepository.save(review);


        Set<Review> reviewSet = reviewRepository.findByProduct(product);

        assertEquals(2, reviewSet.size());



    }

}