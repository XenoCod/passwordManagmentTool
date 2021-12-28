package com.epam.passwordmanagementrest.service;

import com.epam.passwordmanagementrest.dao.UserDaoImpl;
import com.epam.passwordmanagementrest.dto.LoginDTO;
import com.epam.passwordmanagementrest.dto.RegisterDTO;
import com.epam.passwordmanagementrest.entity.User;
import com.epam.passwordmanagementrest.exception.UserAlreadyExistsException;
import com.epam.passwordmanagementrest.exception.UserDoesNotExistException;
import com.epam.passwordmanagementrest.exception.WrongPasswordException;
import com.epam.passwordmanagementrest.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @InjectMocks
    UserDaoImpl userDaoImpl;

    @Mock
    UserRepository userRepository;

    @Test
    void testRegisterUserThrowExceptionWhenAccountAlreadyExists() {
        RegisterDTO registerDTO = new RegisterDTO("Aditya", "USER12", "Aditya19");
        when(userRepository.findByUserName(any())).thenReturn(Optional.of(new User()));
        Assertions.assertThrows(UserAlreadyExistsException.class, () -> userDaoImpl.registerUser(registerDTO));
    }

    @Test
    void testRegisterAccountDoesNotThrowExceptionWhileNewAccountRegistration() {
        RegisterDTO registerDTO = new RegisterDTO("Aditya", "USER12", "Aditya19");
        User user = new User();
        when(userRepository.save(any())).thenReturn(user);
        when(userRepository.findByUserName(any())).thenReturn(Optional.empty());
        Assertions.assertDoesNotThrow(() -> userDaoImpl.registerUser(registerDTO));
    }


    @Test
    void validateLoginThrowExceptionWhenAccountDoesNotExist() {
        LoginDTO loginDTO = new LoginDTO("USER12", "Aditya19");
        when(userRepository.findByUserName(any())).thenReturn(Optional.empty());
        Assertions.assertThrows(UserDoesNotExistException.class, () -> userDaoImpl.validateLogin(loginDTO));
    }

    @Test
    void validateLoginThrowExceptionWhenWrongPasswordException() {
        LoginDTO loginDTO = new LoginDTO("USER12", "Aditya19");
        Optional<User> user = Optional.of(new User("USER12", "Aditya19"));
        when(userRepository.findByUserName(any())).thenReturn(user);
        Assertions.assertThrows(WrongPasswordException.class, () -> userDaoImpl.validateLogin(loginDTO));
    }

    @Test
    void validateLoginDoesNotThrowExceptionWhileAccountExist() {
        LoginDTO loginDTO = new LoginDTO("USER12", "Aditya19");
        Optional<User> user = Optional.of(new User("USER12", "Aditya19"));
        user.get().setPassword((user.get().getPassword()));
        when(userRepository.findByUserName(any())).thenReturn(user);
        Assertions.assertDoesNotThrow(() -> userDaoImpl.validateLogin(loginDTO));
    }

    @Test
    void validateLoginReturnAccountNameWhileAccountExist() throws UserDoesNotExistException, WrongPasswordException {
        LoginDTO loginDTO = new LoginDTO("USER12", "Aditya19");
        Optional<User> user = Optional.of(new User("Aditya", "USER12", "Aditya19"));
        user.get().setPassword((user.get().getPassword()));
        when(userRepository.findByUserName(any())).thenReturn(user);
        Assertions.assertEquals("Aditya", userDaoImpl.validateLogin(loginDTO));
    }
}
