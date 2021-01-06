package com.dawid.controllers;

import com.dawid.commands.ProductCommand;
import com.dawid.domain.Cart;
import com.dawid.domain.Product;
import com.dawid.services.CategoryService;
import com.dawid.services.ProductService;
import com.dawid.services.ReviewService;
import com.dawid.services.UserService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashSet;
import java.util.Set;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ProductControllerTest {

    @Mock
    ProductService productService;

    @Mock
    ReviewService reviewService;

    @Mock
    UserService userService;

    @Mock
    CategoryService categoryService;


    @Mock
    Cart cart;


    ProductController productController;



    MockMvc mockMvc;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        productController = new ProductController(productService, reviewService, userService, categoryService);
        cart = new Cart();

        mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
    }

    @Test
    public void getProductById() throws Exception {
        ProductCommand product = new ProductCommand();
        product.setId(1L);

        when(productService.getProductCommandById(anyLong())).thenReturn(product);

        mockMvc.perform(get("/product/1/show"))
                .andExpect(status().isOk())
                .andExpect(view().name("product/show"))
                .andExpect(model().attributeExists("product"));
    }

    @Test
    public void getProducts() throws Exception{
        Set<Product> products = new HashSet<>();
        Product product = new Product();
        product.setId(1L);
        products.add(product);

        when(productService.getProducts()).thenReturn(products);

        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(view().name("products/showall"))
                .andExpect(model().attributeExists("products"));

    }


}