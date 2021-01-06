package com.dawid.repositories;

import com.dawid.domain.Category;
import com.dawid.domain.Product;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ProductRepositoryTest {

    @Autowired
    ProductRepository productRepository;
    @Autowired
    CategoryRepository categoryRepository;


    @Test
    public void findAllByDiscountTrue() {
        Product product = new Product();
        product.setId(1L);
        product.setDiscount(true);

        productRepository.save(product);

        product = new Product();
        product.setId(2L);
        product.setDiscount(false);

        productRepository.save(product);


        Set<Product> products = productRepository.findAllByDiscountTrue();

        assertEquals(1, products.size());

    }

    @Test
    public void findAllByRecentTrue() {
        Product product = new Product();
        product.setId(1L);
        product.setRecent(true);

        productRepository.save(product);

        product = new Product();
        product.setId(2L);
        product.setRecent(false);

        productRepository.save(product);


        Set<Product> products = productRepository.findAllByRecentTrue();

        assertEquals(1, products.size());


    }

    @Test
    public void findAllByCategoriesContains() {
        Category category = new Category();
        category.setId(1L);
        category.setDescription("Category");
        categoryRepository.save(category);

        Set<Category> categories = new HashSet<>();
        categories.add(category);

        Product product = new Product();
        product.setId(1L);
        product.setCategories(categories);
        productRepository.save(product);


        product = new Product();
        product.setId(2L);
        product.setCategories(categories);
        productRepository.save(product);


        product = new Product();
        product.setId(3L);
        productRepository.save(product);


        Set<Product> productSet = productRepository.findAllByCategoriesContains(category);

        assertEquals(2, productSet.size());

    }
}