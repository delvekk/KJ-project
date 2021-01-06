package com.dawid.controllers;

import com.dawid.domain.User;
import com.dawid.services.EmailService;
import com.dawid.services.UserService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class RegisterControllerTest {

    RegisterController registerController;

    @Mock
    UserService userService;

    @Mock
    EmailService emailService;

    @Mock
    JavaMailSender javaMailSender;

    MockMvc mockMvc;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        registerController = new RegisterController(userService, emailService);

        mockMvc = MockMvcBuilders.standaloneSetup(registerController).build();

    }

    @Test
    public void registerTest() throws Exception {

        mockMvc.perform(get("/register"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("user"))
                .andExpect(view().name("registerform"));


    }

    @Test
    public void registerPostTest() throws Exception {

        mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "")
                .param("email", "email@email.com")
                .param("password", "password")
                .param("name", "name")
                .param("lastname", "lastname")
                .param("active", "1")
                .param("confirmationToken", "token")
                .param("address", "address"))
                .andExpect(status().isOk())
                .andExpect(view().name("registerform"))
                .andExpect(model().attributeExists("user"));
    }

    @Test
    public void showConfirmationPageTest() throws Exception{
        User user = new User();
        user.setId(1L);
        user.setActive(0);
        user.setConfirmationToken("Token");

        when(userService.findByConfirmationToken(any())).thenReturn(user);


        mockMvc.perform(get("/confirm").param("token", "blabla"))
                .andExpect(status().isOk())
                .andExpect(view().name("loginform"))
                .andExpect(model().attributeExists("confirmation"));

    }

}