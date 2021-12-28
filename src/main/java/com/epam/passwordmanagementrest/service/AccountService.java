package com.epam.passwordmanagementrest.service;

import com.epam.passwordmanagementrest.dao.AccountDao;
import com.epam.passwordmanagementrest.dto.AccountDTO;
import com.epam.passwordmanagementrest.dto.LoginDTO;
import com.epam.passwordmanagementrest.entity.Account;
import com.epam.passwordmanagementrest.entity.User;
import com.epam.passwordmanagementrest.exception.*;

import java.util.List;

public interface AccountService {
    Account addAccount(AccountDTO accountDTO) throws UnableToAddAccount, AccountAlreadyExistsException, NoGroupFoundForUser;

    Account findAccountBasedOnUrl(String url) throws NoAccountFoundForUserBasedOnUrl;

    List<Account> findAllAccount() throws NoAccountFoundForUser;

    Account updateAccount(AccountDTO accountDTO) throws UnableToUpdateAccount, NoAccountFoundForUserBasedOnUrl;

    void setUser(LoginDTO loginDTO);

    User getUser();

    Account deleteAccount(int id) throws NoAccountFoundForUserBasedOnId;
}
