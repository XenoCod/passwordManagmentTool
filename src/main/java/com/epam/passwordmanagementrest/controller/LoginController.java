package com.epam.passwordmanagementrest.controller;

import com.epam.passwordmanagementrest.dao.UserDaoImpl;
import com.epam.passwordmanagementrest.dto.LoginDTO;
import com.epam.passwordmanagementrest.exception.UserDoesNotExistException;
import com.epam.passwordmanagementrest.exception.WrongPasswordException;
import com.epam.passwordmanagementrest.service.AccountServiceImpl;
import com.epam.passwordmanagementrest.service.GroupServiceImpl;
import com.epam.passwordmanagementrest.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@RestController
public class LoginController {
    @Autowired
    UserServiceImpl userServiceImpl;

    @Autowired
    GroupServiceImpl groupServiceImpl;

    @Autowired
    AccountServiceImpl accountServiceImpl;

    @PostMapping(value = "/api/loginUser")
    public ResponseEntity<String> loginUser(@RequestBody @Valid LoginDTO loginDTO, HttpSession session) throws UserDoesNotExistException, WrongPasswordException {
        String userName = userServiceImpl.validateLogin(loginDTO);
        groupServiceImpl.setUser(loginDTO);
        accountServiceImpl.setUser(loginDTO);
        session.setAttribute("accountName", userName);
        session.setAttribute("user", loginDTO);
        return new ResponseEntity<>(userName, HttpStatus.OK);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAllException(Exception ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_ACCEPTABLE);
    }
}

