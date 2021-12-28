package com.epam.passwordmanagementrest.controller;

import com.epam.passwordmanagementrest.dto.LoginDTO;
import com.epam.passwordmanagementrest.exception.UserDoesNotExistException;
import com.epam.passwordmanagementrest.exception.WrongPasswordException;
import com.epam.passwordmanagementrest.service.AccountServiceImpl;
import com.epam.passwordmanagementrest.service.GroupServiceImpl;
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

@WebMvcTest(LoginController.class)
class LoginControllerTest {
    @Autowired
    MockMvc mvc;

    @MockBean
    AccountServiceImpl accountServiceImpl;
    @MockBean
    GroupServiceImpl groupServiceImpl;
    @MockBean
    UserServiceImpl userServiceImpl;

    @Test
    public void testLoginUserThrowExceptionWhenAccountDoesNotExist() throws Exception {
        LoginDTO loginDTO = new LoginDTO("USER12","Aditya19");
        when(userServiceImpl.validateLogin(any())).thenThrow(new UserDoesNotExistException());

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/api/loginUser")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(loginDTO));

        MvcResult mvcResult = mvc.perform(requestBuilder).andReturn();

        int status = mvcResult.getResponse().getStatus();
        Assertions.assertEquals(406, status);
        String content = mvcResult.getResponse().getContentAsString();
        Assertions.assertEquals("This user does not exists..Please create a user", content);
    }

    @Test
    public void testLoginUserThrowExceptionWhenWrongPasswordEntered() throws Exception {
        LoginDTO loginDTO = new LoginDTO("USER12","Aditya1");
        when(userServiceImpl.validateLogin(any())).thenThrow(new WrongPasswordException());

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/api/loginUser")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(loginDTO));

        MvcResult mvcResult = mvc.perform(requestBuilder).andReturn();

        int status = mvcResult.getResponse().getStatus();
        Assertions.assertEquals(406, status);
        String content = mvcResult.getResponse().getContentAsString();
        Assertions.assertEquals("Wrong password.!!", content);
    }

    @Test
    public void testLoginUserDoesNotThrowExceptionWithValidCredentials() throws Exception {
        String accountName = "Aditya";
        LoginDTO loginDTO = new LoginDTO("USER12","Aditya19");
        when(userServiceImpl.validateLogin(any())).thenReturn(accountName);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/api/loginUser")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(loginDTO));

        MvcResult mvcResult = mvc.perform(requestBuilder).andReturn();

        int status = mvcResult.getResponse().getStatus();
        Assertions.assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        Assertions.assertEquals(accountName, content);
    }
}