package com.dawid.converters;

import com.dawid.commands.ProductCommand;
import com.dawid.domain.Category;
import com.dawid.domain.Product;
import com.dawid.domain.Review;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ProductToProductCommandTest {
    public static final Long PRODUCT_ID = 1L;
    public static final String NAME = "product";
    public static final Integer PRICE = 100;
    public static final Integer AMOUNT = 10;
    public static final Boolean DISCOUNT = true;
    public static final Boolean RECENT = true;
    public static final String DESCRIPTION = "descr";
    public static final String IMAGE = "image";
    public static final Long CATEGORY_ID = 1L;
    public static final Long REVWIEV_ID = 1L;

    ProductToProductCommand converter;

    @Before
    public void setUp() throws Exception {
        converter = new ProductToProductCommand(new CategoryToCategoryCommand(), new ReviewToReviewCommand(new UserToUserCommand(new RoleToRoleCommand())));
    }

    @Test
    public void convert() {
        Product product = new Product();
        product.setId(PRODUCT_ID);
        product.setName(NAME);
        product.setPrice(PRICE);
        product.setAmount(AMOUNT);
        product.setDiscount(DISCOUNT);
        product.setRecent(RECENT);
        product.setDescription(DESCRIPTION);
        product.setImage_url(IMAGE);

        Category category = new Category();
        category.setId(CATEGORY_ID);

        product.getCategories().add(category);

        Review review = new Review();
        review.setId(REVWIEV_ID);

        product.getComments().add(review);



        ProductCommand command = converter.convert(product);



        assertEquals(PRODUCT_ID, command.getId());
        assertEquals(NAME, command.getName());
        assertEquals(PRICE, command.getPrice());
        assertEquals(AMOUNT, command.getAmount());
        assertEquals(DISCOUNT, command.getDiscount());
        assertEquals(RECENT, command.getRecent());
        assertEquals(DESCRIPTION, command.getDescription());
        assertEquals(IMAGE, command.getImage_url());
        assertEquals(1, command.getCategories().size());
        assertEquals(1, command.getComments().size());


    }
}