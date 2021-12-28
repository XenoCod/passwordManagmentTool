package com.epam.passwordmanagementrest.dao;

import com.epam.passwordmanagementrest.converter.Convert;
import com.epam.passwordmanagementrest.dto.AccountDTO;
import com.epam.passwordmanagementrest.dto.LoginDTO;
import com.epam.passwordmanagementrest.entity.Account;
import com.epam.passwordmanagementrest.entity.Group;
import com.epam.passwordmanagementrest.entity.User;
import com.epam.passwordmanagementrest.exception.*;
import com.epam.passwordmanagementrest.repository.AccountRepository;
import com.epam.passwordmanagementrest.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AccountDaoImpl implements AccountDao{
    @Autowired
    AccountRepository accountRepository;

    @Autowired
    GroupRepository groupRepository;

    private User user;

    @Override
    public Account addAccount(AccountDTO accountDTO) throws AccountAlreadyExistsException, NoGroupFoundForUser {
        Account account = Convert.convertToEntity(accountDTO);
        account.setUser(user);
        accountRepository.findByUrlAndUser(account.getUrl(), account.getUser())
                .ifPresent(s -> {
                    try {
                        throw new AccountAlreadyExistsException();
                    } catch (AccountAlreadyExistsException e) {
                        e.printStackTrace();
                    }
                });
        Group existGroup = groupRepository.findByNameAndUser(accountDTO.getGroupName(), account.getUser())
                .orElseThrow(NoGroupFoundForUser::new);
        account.setGroup(existGroup);
        return accountRepository.save(account);
    }

    @Override
    public Account findAccountByUrl(String url) throws NoAccountFoundForUserBasedOnUrl {
        return accountRepository.findByUrlAndUser(url, user)
                .orElseThrow(NoAccountFoundForUserBasedOnUrl::new);
    }

    @Override
    public List<Account> findAllAccounts() throws NoAccountFoundForUser {
        List<Account> accountList = accountRepository.findByUser(user);
        if (accountList.isEmpty()) {
            throw new NoAccountFoundForUser();
        }
        return accountList;
    }

    @Override
    public Account updateAccount(AccountDTO accountDTO) throws NoAccountFoundForUserBasedOnUrl {
        Account account = Convert.convertToEntity(accountDTO);
        account.setUser(user);
        Account foundAccount = accountRepository.findByUrlAndUser(account.getUrl(), user)
                .orElseThrow(NoAccountFoundForUserBasedOnUrl::new);
        foundAccount.setUserName(account.getUserName());
        foundAccount.setPassword(account.getPassword());
        return accountRepository.save(foundAccount);
    }

    @Override
    public void setUser(LoginDTO loginDTO) {
        this.user = Convert.convertToEntity(loginDTO);
    }

    @Override
    public Account deleteAccount(int id) throws NoAccountFoundForUserBasedOnId {
        Account fetchAccounts = accountRepository.findByIdAndUser(id, user)
                .orElseThrow(NoAccountFoundForUserBasedOnId::new);
        accountRepository.deleteByIdAndUser(id, user);
        return fetchAccounts;
    }

    @Override
    public User getUser() {
        return this.user;
    }
}
