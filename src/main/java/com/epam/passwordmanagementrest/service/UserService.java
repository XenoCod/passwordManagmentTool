package com.epam.passwordmanagementrest.service;

import com.epam.passwordmanagementrest.dto.LoginDTO;
import com.epam.passwordmanagementrest.dto.RegisterDTO;
import com.epam.passwordmanagementrest.entity.User;
import com.epam.passwordmanagementrest.exception.UserAlreadyExistsException;
import com.epam.passwordmanagementrest.exception.UserDoesNotExistException;
import com.epam.passwordmanagementrest.exception.WrongPasswordException;

public interface UserService {
    User registerUser(RegisterDTO registerDTO) throws UserAlreadyExistsException;
    String validateLogin(LoginDTO loginDTO) throws UserDoesNotExistException, WrongPasswordException;
}
