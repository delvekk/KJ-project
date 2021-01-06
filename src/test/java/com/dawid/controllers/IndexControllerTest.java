package com.dawid.controllers;

import com.dawid.domain.Product;
import com.dawid.services.ProductService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class IndexControllerTest {

    @Mock
    ProductService productService;

    @Mock
    Model model;


    IndexController indexController;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        indexController = new IndexController(productService);
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(indexController).build();
    }


    @Test
    public void getIndexItem() throws Exception {
        Set<Product> productSet = new HashSet<>();
        Product product = new Product();
        product.setId(1L);
        productSet.add(product);

        Product product2 = new Product();
        product2.setId(2L);
        productSet.add(product2);


        when(productService.getByDiscount()).thenReturn(productSet);
        when(productService.getByNew()).thenReturn(productSet);

        ArgumentCaptor<Set<Product>> argumentCaptor = ArgumentCaptor.forClass(Set.class);


        String viewName = indexController.getIndexItem(model);



        assertEquals("index", viewName);
        verify(productService, times(1)).getByDiscount();
        verify(productService, times(1)).getByNew();
        verify(model, times(1)).addAttribute(eq("discountProducts"), argumentCaptor.capture());
        verify(model, times(1)).addAttribute(eq("newProducts"), argumentCaptor.capture());
        Set<Product> setInController = argumentCaptor.getValue();
        assertEquals(2, setInController.size());


    }
}