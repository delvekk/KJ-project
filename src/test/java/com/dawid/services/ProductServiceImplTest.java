package com.dawid.services;

import com.dawid.commands.ProductCommand;
import com.dawid.converters.ProductCommandToProduct;
import com.dawid.converters.ProductToProductCommand;
import com.dawid.domain.Category;
import com.dawid.domain.Product;
import com.dawid.repositories.ProductRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class ProductServiceImplTest {

    ProductServiceImpl productService;

    @Mock
    ProductRepository productRepository;

    @Mock
    ProductToProductCommand productToProductCommand;

    @Mock
    ProductCommandToProduct productCommandToProduct;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        productService = new ProductServiceImpl(productRepository, productToProductCommand, productCommandToProduct);
    }

    @Test
    public void getByDiscount() {
        Set<Product> productSet = new HashSet<>();
        Product product1 = new Product();
        product1.setId(1L);
        productSet.add(product1);

        when(productRepository.findAllByDiscountTrue()).thenReturn(productSet);

        Set<Product> products = productService.getByDiscount();

        assertEquals(productSet.size(), products.size());
        verify(productRepository,times(1)).findAllByDiscountTrue();

    }

    @Test
    public void getProductById() {
        Product product = new Product();
        product.setId(1L);

        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));

        Product found = productService.getProductById(anyLong());

        assertEquals(product.getId(), found.getId());
        verify(productRepository, times(1)).findById(anyLong());
    }

    @Test
    public void getProducts() {
        Product product = new Product();
        product.setId(1L);
        product.setName("Name");
        List<Product> products = new ArrayList<>();
        products.add(product);

        when(productRepository.findAll()).thenReturn(products);


        Set<Product> productSet = productService.getProducts();

        assertEquals(1, productSet.size());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    public void getByNew() {
        Set<Product> productSet = new HashSet<>();
        Product product1 = new Product();
        product1.setId(1L);
        productSet.add(product1);

        when(productRepository.findAllByRecentTrue()).thenReturn(productSet);

        Set<Product> products = productService.getByNew();

        assertEquals(productSet.size(), products.size());
        verify(productRepository,times(1)).findAllByRecentTrue();
}

    @Test
    public void getProductCommandById() {
        Product product = new Product();
        product.setId(1L);

        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));

        ProductCommand command = new ProductCommand();
        command.setId(1L);

        when(productToProductCommand.convert(any())).thenReturn(command);

        ProductCommand commandById = productService.getProductCommandById(anyLong());

        assertEquals(product.getId(), commandById.getId());
        verify(productRepository, times(1)).findById(anyLong());
        verify(productToProductCommand, times(1)).convert(any());

    }

    @Test
    public void updateProductCommand() throws Exception {
        ProductCommand command = new ProductCommand();
        command.setId(1L);
        command.setAmount(10);

        Product product = new Product();
        product.setId(1L);
        product.setAmount(10);


        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));

        ProductCommand converted = new ProductCommand();
        converted.setId(1L);
        converted.setAmount(9);

        when(productToProductCommand.convert(any())).thenReturn(converted);


        ProductCommand saved = productService.updateProductCommand(command);

        assertEquals(saved.getId(), converted.getId());
        assertEquals(converted.getAmount(), saved.getAmount());

    }

    @Test
    public void relatedProducts() throws Exception {
        ProductCommand productCommand = new ProductCommand();
        productCommand.setId(1L);



        Product product = new Product();
        product.setId(productCommand.getId());

        Category category = new Category();
        category.setId(1L);
        product.getCategories().add(category);

        when(productCommandToProduct.convert(any())).thenReturn(product);


        Product product1 = new Product();
        product1.setId(2L);

        Product product2 = new Product();
        product2.setId(3L);

        Set<Product> products = new HashSet<>();
        products.add(product1);
        products.add(product2);

        when(productRepository.findAllByCategoriesContains(any())).thenReturn(products);

        Set<ProductCommand> productCommands = productService.relatedProducts(productCommand);

        assertEquals(1, productCommands.size());
        verify(productRepository,times(1)).findAllByCategoriesContains(any());

    }


    @Test
    public void searchedProducts() throws Exception{
        String string = "s9";

        List<Product> products = new ArrayList<>();
        Product product = new Product();
        product.setId(1L);
        product.setName("Galaxy s9");

        Product product1 = new Product();
        product1.setId(2L);
        product1.setName("S7");

        products.add(product);
        products.add(product1);




        when(productRepository.findAll()).thenReturn(products);


        Set<ProductCommand> commands = productService.searchedProducts(string);

        assertEquals(1, commands.size());
        verify(productRepository, times(1)).findAll();
        verify(productToProductCommand, times(1)).convert(any());
    }


    @Test
    public void saveProductCommand(){
        ProductCommand productCommand = new ProductCommand();
        productCommand.setId(1L);

        Product product = new Product();
        product.setId(1L);

        when(productCommandToProduct.convert(any())).thenReturn(product);

        productService.saveProductCommand(productCommand);

        verify(productRepository, times(1)).save(any());
    }


    @Test
    public void getAllByCategory(){
        Category category = new Category();
        category.setId(1L);

        Product product1 = new Product();
        product1.setId(1L);

        Product product2 = new Product();
        product2.setId(2L);

        Set<Product> products = new HashSet<>();
        products.add(product1);
        products.add(product2);


        when(productRepository.findAllByCategoriesContains(any())).thenReturn(products);

        Set<Product> productSet = productService.getAllByCategory(category);

        assertEquals(products.size(), productSet.size());
        verify(productRepository, times(1)).findAllByCategoriesContains(any());


    }

}