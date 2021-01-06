package com.dawid.converters;

import com.dawid.commands.OrderCommand;
import com.dawid.domain.Order;
import com.dawid.domain.Product;
import com.dawid.domain.User;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;

public class OrderToOrderCommandTest {

    public static final Long ORDER_ID = Long.valueOf(1L);
    public static final Date DATE = new Date();
    public static final Integer PRICE = 200;
    public static final Long USER_ID = 1L;
    public static final String ADDRESS = "address";
    public static final String EMAIL = "email";
    public static final String NAME = "name";
    public static final String LASTNAME = "lastname";
    public static final Long PRODUCT_ID = 1L;

    OrderToOrderCommand converter;

    @Before
    public void setUp() throws Exception {
        converter = new OrderToOrderCommand(new ProductToProductCommand(new CategoryToCategoryCommand(), new ReviewToReviewCommand(new UserToUserCommand(new RoleToRoleCommand()))));
    }

    @Test
    public void convert() {
        Order order = new Order();
        order.setId(ORDER_ID);
        order.setDate(DATE);
        order.setPrice(PRICE);
        order.setAddress(ADDRESS);
        order.setEmail(EMAIL);
        order.setName(NAME);
        order.setLastName(LASTNAME);

        Product product = new Product();
        product.setId(PRODUCT_ID);
        order.getProducts().add(product);

        User user = new User();
        user.setId(USER_ID);
        order.setUserInfo(user);

        OrderCommand orderCommand = converter.convert(order);

        assertEquals(ORDER_ID, orderCommand.getId());
        assertEquals(DATE, orderCommand.getDate());
        assertEquals(PRICE, orderCommand.getPrice());
        assertEquals(ADDRESS, orderCommand.getAddress());
        assertEquals(EMAIL, orderCommand.getEmail());
        assertEquals(NAME, orderCommand.getName());
        assertEquals(LASTNAME, orderCommand.getLastName());
        assertEquals(1, orderCommand.getProducts().size());
        assertEquals(USER_ID, orderCommand.getUserInfo().getId());



    }
}