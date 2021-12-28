package com.epam.passwordmanagementrest.controller;

import com.epam.passwordmanagementrest.dto.RegisterDTO;
import com.epam.passwordmanagementrest.entity.User;
import com.epam.passwordmanagementrest.exception.UserAlreadyExistsException;
import com.epam.passwordmanagementrest.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;

@RestController
public class RegisterController {

    @Autowired
    UserServiceImpl userServiceImpl;

    private static final String ACCOUNT_ADDED = "User Registered Successfully!";


    @PostMapping(value = "/api/registerUser")
    public ResponseEntity<String> registerUser(@RequestBody @Valid RegisterDTO registerDTO) throws UserAlreadyExistsException {
        User registerUser = userServiceImpl.registerUser(registerDTO);
        return new ResponseEntity<>(ACCOUNT_ADDED + " " + registerUser, HttpStatus.OK);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAllException(Exception ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_ACCEPTABLE);
    }
}

