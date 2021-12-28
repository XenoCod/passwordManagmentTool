package com.epam.passwordmanagementrest.controller;

import com.epam.passwordmanagementrest.dto.RegisterDTO;
import com.epam.passwordmanagementrest.entity.User;
import com.epam.passwordmanagementrest.exception.UserAlreadyExistsException;
import com.epam.passwordmanagementrest.service.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebMvcTest(RegisterController.class)
class RegisterControllerTest {
    @Autowired
    MockMvc mvc;

    @MockBean
    UserServiceImpl userServiceImpl;

    @Test
    public void testRegisterUserThrowExceptionWhenUserAlreadyExists() throws Exception {
        RegisterDTO registerDTO = new RegisterDTO("Aditya", "USER12", "Aditya19");
        when(userServiceImpl.registerUser(any())).thenThrow(new UserAlreadyExistsException());
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/api/registerUser")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(registerDTO));

        MvcResult mvcResult = mvc.perform(requestBuilder).andReturn();

        int status = mvcResult.getResponse().getStatus();
        Assertions.assertEquals(406, status);
        String content = mvcResult.getResponse().getContentAsString();
        Assertions.assertEquals("This user already exists..Please Log in!!!", content);
    }

    @Test
    public void testRegisterUserDoesNotThrowExceptionWhileRegisteringSuccessfully() throws Exception {
        final String ACCOUNT_ADDED = "User Registered Successfully!";
        RegisterDTO registerDTO = new RegisterDTO("Aditya", "USER12", "Aditya19");
        User registeredUser = new User();
        Object accountServiceImpl;
        when(userServiceImpl.registerUser(any())).thenReturn(registeredUser);
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/api/registerUser")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(registerDTO));

        MvcResult mvcResult = mvc.perform(requestBuilder).andReturn();

        int status = mvcResult.getResponse().getStatus();
        Assertions.assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        Assertions.assertEquals(ACCOUNT_ADDED + " " + registeredUser, content);
    }
}