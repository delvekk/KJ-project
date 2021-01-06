package com.dawid.converters;

import com.dawid.commands.OrderCommand;
import com.dawid.commands.ProductCommand;
import com.dawid.domain.Order;
import com.dawid.domain.User;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;

public class OrderCommandToCommandTest {

    public static final Long ORDER_ID = Long.valueOf(1L);
    public static final Date DATE = new Date();
    public static final Integer PRICE = 200;
    public static final Long USER_ID = 1L;
    public static final String ADDRESS = "address";
    public static final String EMAIL = "email";
    public static final String NAME = "name";
    public static final String LASTNAME = "lastname";
    public static final Long PRODUCT_ID = 1L;

    OrderCommandToCommand converter;

    @Before
    public void setUp() throws Exception {
        converter = new OrderCommandToCommand(new ProductCommandToProduct(new CategoryCommandToCategory(), new ReviewCommandToReview(new UserCommandToUser(new RoleCommandToRole()))));
    }

    @Test
    public void convert() {
        OrderCommand command = new OrderCommand();
        command.setId(ORDER_ID);
        command.setDate(DATE);
        command.setPrice(PRICE);
        command.setAddress(ADDRESS);
        command.setEmail(EMAIL);
        command.setName(NAME);
        command.setLastName(LASTNAME);

        ProductCommand productCommand = new ProductCommand();
        productCommand.setId(PRODUCT_ID);
        command.getProducts().add(productCommand);

        User user = new User();
        user.setId(USER_ID);
        command.setUserInfo(user);

        Order order = converter.convert(command);

        assertEquals(ORDER_ID, order.getId());
        assertEquals(DATE, order.getDate());
        assertEquals(PRICE, order.getPrice());
        assertEquals(ADDRESS, order.getAddress());
        assertEquals(EMAIL, order.getEmail());
        assertEquals(NAME, order.getName());
        assertEquals(LASTNAME, order.getLastName());
        assertEquals(1, order.getProducts().size());
        assertEquals(USER_ID, order.getUserInfo().getId());



    }
}