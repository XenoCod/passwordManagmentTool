package com.epam.passwordmanagementrest.dao;

import com.epam.passwordmanagementrest.dto.AccountDTO;
import com.epam.passwordmanagementrest.dto.LoginDTO;
import com.epam.passwordmanagementrest.entity.Account;
import com.epam.passwordmanagementrest.entity.User;
import com.epam.passwordmanagementrest.exception.*;

import java.util.List;

public interface AccountDao {
    Account addAccount(AccountDTO accountDTO) throws AccountAlreadyExistsException, NoGroupFoundForUser;

    Account findAccountByUrl(String url) throws NoAccountFoundForUserBasedOnUrl;

    List<Account> findAllAccounts() throws NoAccountFoundForUser;

    Account updateAccount(AccountDTO accountDTO) throws NoAccountFoundForUserBasedOnUrl;

    void setUser(LoginDTO loginDTO);

    Account deleteAccount(int id) throws NoAccountFoundForUserBasedOnId;

    User getUser();
}
