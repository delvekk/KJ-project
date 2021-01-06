package com.dawid.services;

import com.dawid.commands.UserCommand;
import com.dawid.domain.User;
import com.dawid.repositories.RoleRepository;
import com.dawid.repositories.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceImplIT {


    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;


    @Test
    public void saveUser() {
        User user = new User();
        user.setId(1L);
        user.setEmail("mail@mail.com");
        user.setName("name");
        user.setLastname("lastname");
        user.setActive(0);
        user.setPassword("password");

        userService.saveUser(user);

        assertEquals(1, userRepository.findAll().size());

    }

    @Test
    public void saveUserCommand() {
        UserCommand userCommand = new UserCommand();
        userCommand.setId(1L);
        userCommand.setEmail("mail@mail.com");
        userCommand.setName("name");
        userCommand.setLastname("lastname");
        userCommand.setActive(0);
        userCommand.setPassword("password");

        userService.saveUserCommand(userCommand);

        assertEquals(1, userRepository.findAll().size());

    }

    @Test
    public void updateUser() {
        User user = new User();
        user.setId(1L);
        user.setEmail("mail@mail.com");
        user.setName("name");
        user.setLastname("lastname");
        user.setActive(0);
        user.setPassword("password");

        userService.saveUser(user);

        UserCommand userCommand = new UserCommand();
        userCommand.setId(1L);
        userCommand.setEmail("mail@mail.com");
        userCommand.setName("CHANGED_NAME");
        userCommand.setLastname("lastname");
        userCommand.setActive(0);
        userCommand.setPassword("password");

        userService.updateUser(userCommand);

        assertEquals(1, userRepository.findAll().size());



    }
}