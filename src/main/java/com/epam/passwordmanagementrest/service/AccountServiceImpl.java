package com.epam.passwordmanagementrest.service;

import com.epam.passwordmanagementrest.dao.AccountDaoImpl;
import com.epam.passwordmanagementrest.dto.AccountDTO;
import com.epam.passwordmanagementrest.dto.LoginDTO;
import com.epam.passwordmanagementrest.entity.Account;
import com.epam.passwordmanagementrest.entity.User;
import com.epam.passwordmanagementrest.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService{
    @Autowired
    AccountDaoImpl accountDao;

    @Override
    public Account addAccount(AccountDTO accountDTO) throws UnableToAddAccount, AccountAlreadyExistsException, NoGroupFoundForUser {
        return accountDao.addAccount(accountDTO);
    }

    @Override
    public Account findAccountBasedOnUrl(String url) throws NoAccountFoundForUserBasedOnUrl {
        return accountDao.findAccountByUrl(url);
    }

    @Override
    public List<Account> findAllAccount() throws NoAccountFoundForUser {
        return accountDao.findAllAccounts();
    }

    @Override
    public Account updateAccount(AccountDTO accountDTO) throws UnableToUpdateAccount, NoAccountFoundForUserBasedOnUrl {
        return accountDao.updateAccount(accountDTO);
    }

    @Override
    public Account deleteAccount(int id) throws NoAccountFoundForUserBasedOnId {
        return accountDao.deleteAccount(id);
    }

    @Override
    public void setUser(LoginDTO loginDTO) {
        accountDao.setUser(loginDTO);
    }

    @Override
    public User getUser() {
        return accountDao.getUser();
    }
}
