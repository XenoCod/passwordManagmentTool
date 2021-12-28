package com.epam.passwordmanagementrest.controller;

import com.epam.passwordmanagementrest.dao.AccountDao;
import com.epam.passwordmanagementrest.dao.AccountDaoImpl;
import com.epam.passwordmanagementrest.dao.GroupDaoImpl;
import com.epam.passwordmanagementrest.dto.AccountDTO;
import com.epam.passwordmanagementrest.entity.Account;
import com.epam.passwordmanagementrest.entity.Group;
import com.epam.passwordmanagementrest.exception.AccountAlreadyExistsException;
import com.epam.passwordmanagementrest.exception.NoAccountFoundForUser;
import com.epam.passwordmanagementrest.exception.NoAccountFoundForUserBasedOnId;
import com.epam.passwordmanagementrest.exception.NoAccountFoundForUserBasedOnUrl;
import com.epam.passwordmanagementrest.service.AccountService;
import com.epam.passwordmanagementrest.service.GroupServiceImpl;
import com.fasterxml.jackson.core.type.TypeReference;
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

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@WebMvcTest(AccountDaoImpl.class)
class AccountControllerTest {
    @Autowired
    MockMvc mvc;

    @MockBean
    AccountDaoImpl accountServiceImpl;
    @MockBean
    GroupDaoImpl groupServiceImpl;

    @Test
    public void testShowEditAccountForm() throws Exception {
        Account account = new Account("USER12", "Aditya19", "http://www.master.com", "Account1");
        when(accountServiceImpl.findAccountByUrl(any())).thenReturn(account);

        String url = "www.master.com";

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/account/showEditAccountForm/" + url);

        MvcResult mvcResult = mvc.perform(requestBuilder).andReturn();

        int status = mvcResult.getResponse().getStatus();
        Assertions.assertEquals(200, status);
    }

    @Test
    public void testShowNewAccountForm() throws Exception {
        List<Group> groupList = Arrays.asList(new Group("Google"),
                new Group("EpamPvt"));
        when(groupServiceImpl.findAllGroups()).thenReturn(groupList);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/account/showNewAccountForm");

        MvcResult mvcResult = mvc.perform(requestBuilder).andReturn();

        int status = mvcResult.getResponse().getStatus();
        Assertions.assertEquals(200, status);
    }

    @Test
    public void testAddAccountThrowExceptionWhenAccountAlreadyExistsException() throws Exception {
        AccountDTO accountDTO = new AccountDTO("USER12", "Aditya19", "http://www.google.com", "accounts");
        when(accountServiceImpl.addAccount(any())).thenThrow(new AccountAlreadyExistsException());

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/api/account/addAccount")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(accountDTO));

        MvcResult mvcResult = mvc.perform(requestBuilder).andReturn();

        int status = mvcResult.getResponse().getStatus();
        Assertions.assertEquals(406, status);
        String content = mvcResult.getResponse().getContentAsString();
        Assertions.assertEquals("This account already exists", content);
    }

    @Test
    public void testAddAccountDoesNotThrowExceptionWhileAddingNewAccount() throws Exception {
        final String ACCOUNT_ADDED = "Account added successfully!!";
        Account addedAccount = new Account();
        AccountDTO accountDTO = new AccountDTO("USER12", "Aditya19", "http://www.google.com", "accounts");
        when(accountServiceImpl.addAccount(any())).thenReturn(addedAccount);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/api/account/addAccount")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(accountDTO));

        MvcResult mvcResult = mvc.perform(requestBuilder).andReturn();

        int status = mvcResult.getResponse().getStatus();
        Assertions.assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        Assertions.assertEquals(ACCOUNT_ADDED + " " + addedAccount, content);
    }

    @Test
    public void testUpdateAccountThrowExceptionWhenNoAccountFoundForUserBasedOnUrl() throws Exception {
        AccountDTO accountDTO = new AccountDTO("USER12", "Aditya19", "http://www.google.com", "accounts");
        when(accountServiceImpl.updateAccount(any())).thenThrow(new NoAccountFoundForUserBasedOnUrl());

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/api/account/updateAccount")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(accountDTO));

        MvcResult mvcResult = mvc.perform(requestBuilder).andReturn();

        int status = mvcResult.getResponse().getStatus();
        Assertions.assertEquals(406, status);
        String content = mvcResult.getResponse().getContentAsString();
        Assertions.assertEquals("No accounts exists", content);
    }

    @Test
    public void testUpdateAccountDoesNotThrowExceptionWhileUpdatingSuccessfully() throws Exception {
        final String ACCOUNT_UPDATED = "Account updated successfully!!";
        Account updateAccount = new Account();
        AccountDTO accountDTO= new AccountDTO("USER12", "Aditya19", "http://www.google.com", "records");
        when(accountServiceImpl.updateAccount(any())).thenReturn(updateAccount);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/api/account/updateAccount")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(accountDTO));

        MvcResult mvcResult = mvc.perform(requestBuilder).andReturn();

        int status = mvcResult.getResponse().getStatus();
        Assertions.assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        Assertions.assertEquals(ACCOUNT_UPDATED + " " + updateAccount, content);
    }

    @Test
    public void testViewAccountThrowExceptionWhenNoAccountFoundForUser() throws Exception {
        when(accountServiceImpl.findAllAccounts()).thenThrow(new NoAccountFoundForUser());

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/account/viewAccounts");

        MvcResult mvcResult = mvc.perform(requestBuilder).andReturn();

        int status = mvcResult.getResponse().getStatus();
        Assertions.assertEquals(406, status);
        String content = mvcResult.getResponse().getContentAsString();
        Assertions.assertEquals("No account for this user", content);

    }

    @Test
    public void testViewAccountReturnListOfAccountWhileAccountFound() throws Exception {
        List<Account> accountList = List.of(new Account("USER12", "Aditya19", "http://www.google.com", "accounts"));
        when(accountServiceImpl.findAllAccounts()).thenReturn(accountList);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/account/viewAccounts");

        MvcResult mvcResult = mvc.perform(requestBuilder).andReturn();

        int status = mvcResult.getResponse().getStatus();
        Assertions.assertEquals(200, status);
        ObjectMapper mapper = new ObjectMapper();
        List<Account> actual = mapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<>() {
        });
        Assertions.assertEquals(actual.toString(), accountList.toString());
    }

    @Test
    public void testDeleteAccountThrowExceptionWhenNoAccountFoundForUserBasedOnId() throws Exception {
        when(accountServiceImpl.deleteAccount(anyInt())).thenThrow(new NoAccountFoundForUserBasedOnId());

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/api/account/deleteAccount/2");

        MvcResult mvcResult = mvc.perform(requestBuilder).andReturn();

        int status = mvcResult.getResponse().getStatus();
        Assertions.assertEquals(406, status);
        String content = mvcResult.getResponse().getContentAsString();
        Assertions.assertEquals("No account found", content);
    }

    @Test
    public void testDeleteAccountDoesNotThrowExceptionWhileDeletingSuccessfully() throws Exception {
        final String ACCOUNT_DELETED = "Account deleted successfully!!";
        Account deleteAccount = new Account();
        when(accountServiceImpl.deleteAccount(anyInt())).thenReturn(deleteAccount);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/api/account/deleteAccount/2");

        MvcResult mvcResult = mvc.perform(requestBuilder).andReturn();

        int status = mvcResult.getResponse().getStatus();
        Assertions.assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        Assertions.assertEquals(ACCOUNT_DELETED + " " + deleteAccount, content);
    }

}