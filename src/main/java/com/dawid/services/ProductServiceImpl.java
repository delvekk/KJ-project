package com.dawid.services;

import com.dawid.commands.ProductCommand;
import com.dawid.converters.ProductCommandToProduct;
import com.dawid.converters.ProductToProductCommand;
import com.dawid.domain.Category;
import com.dawid.domain.Product;
import com.dawid.exceptions.NotFoundException;
import com.dawid.repositories.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {


    private final ProductRepository productRepository;
    private final ProductToProductCommand productToProductCommand;
    private final ProductCommandToProduct productCommandToProduct;

    public ProductServiceImpl(ProductRepository productRepository, ProductToProductCommand productToProductCommand, ProductCommandToProduct productCommandToProduct) {
        this.productRepository = productRepository;
        this.productToProductCommand = productToProductCommand;
        this.productCommandToProduct = productCommandToProduct;
    }

    @Override
    public Set<Product> getByDiscount() {
        Set<Product> products = productRepository.findAllByDiscountTrue();

        return products;
    }

    @Override
    public Product getProductById(Long l){
        Optional<Product> productOptional = productRepository.findById(l);
        if(!productOptional.isPresent()){
            throw new NotFoundException("Nie znaleziono");
        }
        return productOptional.get();
    }

    @Override
    public Set<Product> getProducts() {
        Set<Product> products = new TreeSet<>(Comparator.comparing(Product::getId));
        productRepository.findAll().stream().iterator().forEachRemaining(products::add);
        return products;
    }



    @Override
    public Set<Product> getByNew() {
        Set<Product> products = productRepository.findAllByRecentTrue();
        //log.debug(Integer.toString(products.size()));

        return products;
    }

    @Override
    public ProductCommand getProductCommandById(Long l) {
        Product product = getProductById(l);
        ProductCommand command = productToProductCommand.convert(product);

        return command;
    }

    @Override
    public ProductCommand updateProductCommand(ProductCommand command) {
        if (command != null) {
            Product product = productRepository.findById(command.getId()).get();
            product.setAmount(product.getAmount() - 1);
            productRepository.save(product);
            return productToProductCommand.convert(product);
        } else {
            return null;
        }

    }

    @Override
    public Set<ProductCommand> relatedProducts(ProductCommand productCommand) {
        Product product = productCommandToProduct.convert(productCommand);
        List<Category> categories = product.getCategories().stream().limit(1).collect(Collectors.toList());
        Set<ProductCommand> productCommands = new HashSet<>();
        if(categories.size()>0) {
            Category category = categories.get(0);
            Set<Product> products = productRepository.findAllByCategoriesContains(category);
            // log.debug(Integer.toString(products.size()));
            products.forEach(product1 -> productCommands.add(productToProductCommand.convert(product1)));
        }
        return productCommands;

    }

    @Override
    public Set<ProductCommand> searchedProducts(String phrase) {
        String[] words = phrase.split("\\W");
        log.debug(Arrays.toString(words));
        HashSet<ProductCommand> productCommands = new HashSet<>();
        List<Product> products = productRepository.findAll();
        for(Product product : products){
            for(String word : words){
                if (product.getName().toLowerCase().contains(word.toLowerCase())){
                    productCommands.add(productToProductCommand.convert(product));
                    break;
                }
            }
        }
        log.debug(Integer.toString(productCommands.size()));
    return productCommands;
    }

    @Override
    public void saveProductCommand(ProductCommand productCommand) {
        Product product = productCommandToProduct.convert(productCommand);

        productRepository.save(product);
    }

    @Override
    public void saveProduct(Product product){
        productRepository.save(product);
    }


    @Override
    public void deleteProductById(Long id) {
        Product product = getProductById(id);
        productRepository.delete(product);
    }

    @Override
    public Set<Product> getAllByCategory(Category category) {
        Set<Product> products = productRepository.findAllByCategoriesContains(category);

        return products;
    }
}
