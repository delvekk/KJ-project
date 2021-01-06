package com.dawid.converters;

import com.dawid.commands.CategoryCommand;
import com.dawid.commands.ProductCommand;
import com.dawid.commands.ReviewCommand;
import com.dawid.domain.Product;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ProductCommandToProductTest {
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

    ProductCommandToProduct converter;


    @Before
    public void setUp() throws Exception {
        converter = new ProductCommandToProduct(new CategoryCommandToCategory(), new ReviewCommandToReview(new UserCommandToUser(new RoleCommandToRole())));
    }

    @Test
    public void convert() {
        ProductCommand productCommand = new ProductCommand();
        productCommand.setId(PRODUCT_ID);
        productCommand.setName(NAME);
        productCommand.setPrice(PRICE);
        productCommand.setAmount(AMOUNT);
        productCommand.setDiscount(DISCOUNT);
        productCommand.setRecent(RECENT);
        productCommand.setDescription(DESCRIPTION);
        productCommand.setImage_url(IMAGE);

        CategoryCommand categoryCommand = new CategoryCommand();
        categoryCommand.setId(CATEGORY_ID);

        productCommand.getCategories().add(categoryCommand);

        ReviewCommand reviewCommand = new ReviewCommand();
        reviewCommand.setId(REVWIEV_ID);

        productCommand.getComments().add(reviewCommand);


        Product product = converter.convert(productCommand);

        assertEquals(PRODUCT_ID, product.getId());
        assertEquals(NAME, product.getName());
        assertEquals(PRICE, product.getPrice());
        assertEquals(AMOUNT, product.getAmount());
        assertEquals(DISCOUNT, product.getDiscount());
        assertEquals(RECENT, product.getRecent());
        assertEquals(DESCRIPTION, product.getDescription());
        assertEquals(IMAGE, product.getImage_url());
        assertEquals(1, product.getCategories().size());
        assertEquals(1, product.getComments().size());

    }
}