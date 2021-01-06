package com.dawid.services;


import com.dawid.commands.ProductCommand;
import com.dawid.domain.Category;
import com.dawid.domain.Product;

import java.util.Set;

public interface ProductService {

    Set<Product> getByDiscount();

    Set<Product> getByNew();

    Product getProductById(Long l);

    Set<Product> getProducts();

    ProductCommand getProductCommandById(Long l);

    ProductCommand updateProductCommand(ProductCommand command);

    Set<ProductCommand> relatedProducts(ProductCommand productCommand);

    Set<ProductCommand> searchedProducts(String phrase);

    void saveProductCommand(ProductCommand productCommand);

    void deleteProductById(Long id);

    Set<Product> getAllByCategory(Category category);

    void saveProduct(Product product);








}
