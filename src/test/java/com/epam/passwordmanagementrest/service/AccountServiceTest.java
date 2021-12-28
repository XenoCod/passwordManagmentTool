package com.epam.passwordmanagementrest.service;

import com.epam.passwordmanagementrest.dao.AccountDaoImpl;
import com.epam.passwordmanagementrest.dto.AccountDTO;
import com.epam.passwordmanagementrest.dto.LoginDTO;
import com.epam.passwordmanagementrest.entity.Account;
import com.epam.passwordmanagementrest.entity.Group;
import com.epam.passwordmanagementrest.entity.User;
import com.epam.passwordmanagementrest.exception.AccountAlreadyExistsException;
import com.epam.passwordmanagementrest.exception.NoAccountFoundForUser;
import com.epam.passwordmanagementrest.exception.NoAccountFoundForUserBasedOnId;
import com.epam.passwordmanagementrest.exception.NoAccountFoundForUserBasedOnUrl;
import com.epam.passwordmanagementrest.repository.AccountRepository;
import com.epam.passwordmanagementrest.repository.GroupRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {
    @InjectMocks
    AccountDaoImpl accountServiceImpl;

    @Mock
    AccountRepository accountRepository;

    @Mock
    GroupRepository groupRepository;

    @Test
    void addAccountThrowExceptionWhenAccountAlreadyExists() {
        AccountDTO accountDTO = new AccountDTO("USER12", "Aditya19", "http://www.epam.com", "first record");
        when(accountRepository.findByUrlAndUser(any(), any())).thenReturn(Optional.of(new Account()));
        Assertions.assertThrows(AccountAlreadyExistsException.class, () -> accountServiceImpl.addAccount(accountDTO));
    }

    @Test
    void addAccountDoesNotThrowExceptionWhileAddingNewAccount() {
        AccountDTO accountDTO= new AccountDTO("USER12", "Aditya19", "http://www.epam.com", "first record");
        when(accountRepository.save(any())).thenReturn(new Account());
        when(groupRepository.findByNameAndUser(any(), any())).thenReturn(Optional.of(new Group()));
        when(accountRepository.findByUrlAndUser(any(), any())).thenReturn(Optional.empty());
        Assertions.assertDoesNotThrow(() -> accountServiceImpl.addAccount(accountDTO));
    }

    @Test
    void findAccountByUrlThrowExceptionWhenNoAccountFoundForProvidedUrl() {
        String url = "http://www.master.com";
        when(accountRepository.findByUrlAndUser(any(), any())).thenReturn(Optional.empty());
        Assertions.assertThrows(NoAccountFoundForUserBasedOnUrl.class, () -> accountServiceImpl.findAccountByUrl(url));
    }

    @Test
    void findAccountByUrlDoesNotThrowExceptionWhileAccountFoundForProvidedUrl() {
        String url = "http://www.master.com";
        when(accountRepository.findByUrlAndUser(any(), any())).thenReturn(Optional.of(new Account()));
        Assertions.assertDoesNotThrow(() -> accountServiceImpl.findAccountByUrl(url));
    }

    @Test
    void findAllAccountsThrowExceptionWhenNoAccountFound() {
        when(accountRepository.findByUser(any())).thenReturn(Collections.emptyList());
        Assertions.assertThrows(NoAccountFoundForUser.class, () -> accountServiceImpl.findAllAccounts());
    }

    @Test
    void findAllAccountsDoesNotThrowExceptionWhileAccountFound() {
        List<Account> accountList = List.of(new Account("USER12", "Aditya19", "http://www.,master.com", "record data"));
        when(accountRepository.findByUser(any())).thenReturn(accountList);
        Assertions.assertEquals(accountList, Assertions.assertDoesNotThrow(() -> accountServiceImpl.findAllAccounts()));
    }

    @Test
    void updateAccountThrowExceptionWhenNoAccountFoundForUserByUrl() {
        AccountDTO accountDTO = new AccountDTO("USER12", "Aditya19", "http://www.epam.com", "first record");
        when(accountRepository.findByUrlAndUser(any(), any())).thenReturn(Optional.empty());
        Assertions.assertThrows(NoAccountFoundForUserBasedOnUrl.class, () -> accountServiceImpl.updateAccount(accountDTO));
    }

    @Test
    void updateAccountDoesNotThrowExceptionWhileAccountFoundForUserBasedOnUrl() {
        AccountDTO accountDTO= new AccountDTO("USER12", "Aditya19", "http://www.epam.com", "first record");
        when(accountRepository.save(any())).thenReturn(new Account());
        when(accountRepository.findByUrlAndUser(any(), any())).thenReturn(Optional.of(new Account()));
        Assertions.assertDoesNotThrow(() -> accountServiceImpl.updateAccount(accountDTO));
    }

    @Test
    void deleteAccountThrowExceptionWhenNoAccountFoundById() {
        when(accountRepository.findByIdAndUser(anyInt(), any())).thenReturn(Optional.empty());
        Assertions.assertThrows(NoAccountFoundForUserBasedOnId.class, () -> accountServiceImpl.deleteAccount(12));
    }

    @Test
    void deleteAccountDoesNotThrowExceptionWhenAccountFoundById() {
        int id = 12;
        Account account = new Account("USER12", "Aditya19", "http://www.epam.com", "first record");
        doNothing().when(accountRepository).deleteByIdAndUser(anyInt(),any());
        when(accountRepository.findByIdAndUser(anyInt(),any())).thenReturn(Optional.of(account));
        Assertions.assertEquals(account, Assertions.assertDoesNotThrow(() -> accountServiceImpl.deleteAccount(id)));
    }

    @Test
    void testAccount() {
        LoginDTO loginDTO = new LoginDTO("USER12", "Aditya19");
        accountServiceImpl.setUser(loginDTO);
        User user= new User("USER12", "Aditya19");
        Assertions.assertEquals(user.getUserName(), accountServiceImpl.getUser().getUserName());
    }
}