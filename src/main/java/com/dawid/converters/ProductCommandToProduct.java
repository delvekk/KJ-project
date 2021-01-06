package com.dawid.converters;

import com.dawid.commands.ProductCommand;
import com.dawid.commands.ReviewCommand;
import com.dawid.domain.Product;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class ProductCommandToProduct implements Converter<ProductCommand, Product> {

    private final CategoryCommandToCategory categoryCommandToCategory;
    private final ReviewCommandToReview reviewCommandToReview;

    public ProductCommandToProduct(CategoryCommandToCategory categoryCommandToCategory, ReviewCommandToReview reviewCommandToReview) {
        this.categoryCommandToCategory = categoryCommandToCategory;
        this.reviewCommandToReview = reviewCommandToReview;
    }

    @Nullable
    @Synchronized
    @Override
    public Product convert(ProductCommand source) {
        if(source == null){
            return null;
        }

        Product product = new Product();
        product.setId(source.getId());
        product.setAmount(source.getAmount());
        product.setDescription(source.getDescription());
        product.setImage_url(source.getImage_url());
        product.setDiscount(source.getDiscount());
        product.setRecent(source.getRecent());
        product.setName(source.getName());
        product.setPrice(source.getPrice());

        source.getCategories()
                .forEach(categoryCommand -> product.getCategories().add(categoryCommandToCategory.convert(categoryCommand)));

        source.getComments()
                .forEach(reviewCommand -> product.getComments().add(reviewCommandToReview.convert(reviewCommand)));

        return product;
    }
}
