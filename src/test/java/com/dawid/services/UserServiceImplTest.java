package com.dawid.services;

import com.dawid.converters.UserCommandToUser;
import com.dawid.converters.UserToUserCommand;
import com.dawid.domain.User;
import com.dawid.repositories.RoleRepository;
import com.dawid.repositories.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class UserServiceImplTest {

    UserServiceImpl userService;

    @Mock
    UserRepository userRepository;

    @Mock
    RoleRepository roleRepository;

    @Mock
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Mock
    UserCommandToUser userCommandToUser;

    @Mock
    UserToUserCommand userToUserCommand;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        userService = new UserServiceImpl(userRepository,roleRepository,bCryptPasswordEncoder, userCommandToUser, userToUserCommand);
    }

    @Test
    public void findUserByEmail() throws Exception {
        User user = new User();
        user.setId(1L);
        user.setEmail("mail");

        when(userRepository.findByEmail(anyString())).thenReturn(user);

        User found = userService.findUserByEmail(anyString());

        assertEquals(user.getId(),found.getId());
        assertEquals(user.getEmail(), found.getEmail());
        verify(userRepository, times(1)).findByEmail(anyString());
    }


    @Test
    public void findByConfirmationToken() throws Exception {
        User user = new User();
        user.setId(1L);
        user.setConfirmationToken("token");

        when(userRepository.findByConfirmationToken(anyString())).thenReturn(user);

        User found = userService.findByConfirmationToken(anyString());

        assertEquals(user.getId(),found.getId());
        assertEquals(user.getConfirmationToken(), found.getConfirmationToken());
        verify(userRepository, times(1)).findByConfirmationToken(anyString());
    }




}