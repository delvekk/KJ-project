package com.dawid.converters;

import com.dawid.commands.ProductCommand;
import com.dawid.domain.Category;
import com.dawid.domain.Product;
import com.dawid.domain.Review;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class ProductToProductCommand implements Converter<Product, ProductCommand> {


    private final CategoryToCategoryCommand categoryToCategoryCommand;
    private final ReviewToReviewCommand reviewToReviewCommand;

    public ProductToProductCommand(CategoryToCategoryCommand categoryToCategoryCommand, ReviewToReviewCommand reviewToReviewCommand) {
        this.categoryToCategoryCommand = categoryToCategoryCommand;
        this.reviewToReviewCommand = reviewToReviewCommand;
    }


    @Synchronized
    @Nullable
    @Override
    public ProductCommand convert(Product source) {
        if (source == null) {
            return null;
        }

        ProductCommand command = new ProductCommand();
        command.setId(source.getId());
        command.setAmount(source.getAmount());
        command.setDescription(source.getDescription());
        command.setImage_url(source.getImage_url());
        command.setDiscount(source.getDiscount());
        command.setRecent(source.getRecent());
        command.setPrice(source.getPrice());
        command.setName(source.getName());

        if (source.getCategories() != null && source.getCategories().size() > 0) {
            source.getCategories()
                    .forEach((Category category) -> command.getCategories().add(categoryToCategoryCommand.convert(category)));
        }

        if (source.getComments() != null && source.getComments().size() > 0) {
            source.getComments()
                    .forEach((Review comment) -> command.getComments().add(reviewToReviewCommand.convert(comment)));
        }

        return command;

    }
}
