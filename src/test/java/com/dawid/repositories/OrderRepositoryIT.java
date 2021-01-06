package com.dawid.repositories;

import com.dawid.domain.Order;
import com.dawid.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Set;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
public class OrderRepositoryIT {

    @Autowired
    OrderRepository orderRepository;

    @Test
    public void findByUserInfo() {
        User user = new User();
        user.setId(1L);

        Set<Order> orderSet = orderRepository.findByUserInfo(user);

        assertEquals(0,orderSet.size());

    }
}