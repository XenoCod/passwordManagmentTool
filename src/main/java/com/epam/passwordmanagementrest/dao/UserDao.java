package com.epam.passwordmanagementrest.dao;

import com.epam.passwordmanagementrest.dto.LoginDTO;
import com.epam.passwordmanagementrest.dto.RegisterDTO;
import com.epam.passwordmanagementrest.entity.User;
import com.epam.passwordmanagementrest.exception.UserAlreadyExistsException;
import com.epam.passwordmanagementrest.exception.UserDoesNotExistException;
import com.epam.passwordmanagementrest.exception.WrongPasswordException;

public interface UserDao {
    User registerUser(RegisterDTO registerDTO) throws UserAlreadyExistsException;
    String validateLogin(LoginDTO loginDTO) throws UserDoesNotExistException, WrongPasswordException;
}
