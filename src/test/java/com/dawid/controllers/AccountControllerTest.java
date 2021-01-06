package com.dawid.controllers;


import com.dawid.services.OrderService;
import com.dawid.services.UserService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

public class AccountControllerTest {


    AccountController accountController;

    @Mock
    UserService userService;

    @Mock
    OrderService orderService;


    MockMvc mockMvc;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        accountController = new AccountController(userService, orderService);

        mockMvc = MockMvcBuilders.standaloneSetup(accountController).build();
    }


    @Test
    public void update() throws Exception {


        mockMvc.perform(post("/update")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "")
                .param("email", "email@email.com")
                .param("password", "password")
                .param("name", "name")
                .param("lastname", "lastname")
                .param("active", "1")
                .param("confirmationToken", "token")
                .param("address", "address"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/user/account"));
    }
}