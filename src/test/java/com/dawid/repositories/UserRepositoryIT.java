package com.dawid.repositories;

import com.dawid.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryIT {

    @Autowired
    UserRepository userRepository;


    @Test
    public void findByEmail() {
        User user = userRepository.findByEmail("email@email.com");

        assertEquals("email@email.com", user.getEmail());

    }


    @Test
    public void findByConfirmationToken() {
        String TOKEN = "TOKEN";
        User user = new User();
        user.setEmail("test@test.com");
        user.setLastname("Lastname");
        user.setName("Name");
        user.setId(1L);
        user.setConfirmationToken(TOKEN);
        userRepository.save(user);

        User found = userRepository.findByConfirmationToken(TOKEN);

        assertEquals(user.getId(), found.getId());
    }
}