package com.epam.passwordmanagementrest.controller;

import com.epam.passwordmanagementrest.dto.AccountDTO;
import com.epam.passwordmanagementrest.entity.Account;
import com.epam.passwordmanagementrest.entity.Group;
import com.epam.passwordmanagementrest.exception.*;
import com.epam.passwordmanagementrest.service.AccountServiceImpl;
import com.epam.passwordmanagementrest.service.GroupServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = {"", "api/account"})
public class AccountController {
    @Autowired
    AccountServiceImpl accountServiceImpl;
    @Autowired
    GroupServiceImpl groupServiceImpl;

    private static final String RECORD_UPDATED = "Record updated successfully!!";
    private static final String RECORD_ADDED = "Record added successfully!!";
    private static final String RECORD_DELETED = "Record deleted successfully!!";

    @GetMapping(value = "/viewAccounts")
    public ResponseEntity<List<Account>> getUserAccountsList() throws NoAccountFoundForUser {
        List<Account> accountList = accountServiceImpl.findAllAccount();
        return new ResponseEntity<>(accountList, HttpStatus.OK);
    }

    @GetMapping(value = "showEditAccountForm/{url}")
    public ResponseEntity<Object> getEditAccountForm(@PathVariable String url) throws NoAccountFoundForUserBasedOnUrl {
        Account account = accountServiceImpl.findAccountBasedOnUrl(url);
        return new ResponseEntity<>(account, HttpStatus.OK);
    }

    @PutMapping(value = "/updateAccount")
    public ResponseEntity<Object> updateAccount(@RequestBody @Valid AccountDTO accountDTO) throws UnableToUpdateAccount, NoAccountFoundForUserBasedOnUrl {
        Account updatedAccount = accountServiceImpl.updateAccount(accountDTO);
        return new ResponseEntity<>(RECORD_UPDATED + " " + updatedAccount, HttpStatus.OK);
    }

    @GetMapping(value = "showNewAccountForm")
    public ResponseEntity<List<String>> getNewAccountForm() throws NoGroupFoundForUser {
        return new ResponseEntity<>(getGroupNameList(), HttpStatus.OK);
    }

    private List<String> getGroupNameList() throws NoGroupFoundForUser, NoGroupFoundForUser {
        List<Group> groupList = groupServiceImpl.findAllGroups();
        return groupList.stream().map(Group::getName).collect(Collectors.toList());
    }

    @PostMapping(value = "/addAccount")
    public ResponseEntity<Object> addAccount(@RequestBody @Valid AccountDTO accountDTO) throws UnableToAddAccount, AccountAlreadyExistsException, NoGroupFoundForUser {
        Account account = accountServiceImpl.addAccount(accountDTO);
        return new ResponseEntity<>(RECORD_ADDED + " " + account, HttpStatus.OK);
    }

    @DeleteMapping(value = "/deleteAccount/{id}")
    public ResponseEntity<Object> deleteAccount(@PathVariable int id) throws NoAccountFoundForUserBasedOnId {
        Account deletedAccount = accountServiceImpl.deleteAccount(id);
        return new ResponseEntity<>(RECORD_DELETED + " " + deletedAccount, HttpStatus.OK);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAllException(Exception ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_ACCEPTABLE);
    }
}