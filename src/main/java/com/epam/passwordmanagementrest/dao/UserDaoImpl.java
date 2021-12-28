package com.epam.passwordmanagementrest.dao;

import com.epam.passwordmanagementrest.converter.Convert;
import com.epam.passwordmanagementrest.dto.LoginDTO;
import com.epam.passwordmanagementrest.dto.RegisterDTO;
import com.epam.passwordmanagementrest.entity.User;
import com.epam.passwordmanagementrest.exception.UserAlreadyExistsException;
import com.epam.passwordmanagementrest.exception.UserDoesNotExistException;
import com.epam.passwordmanagementrest.exception.WrongPasswordException;
import com.epam.passwordmanagementrest.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserDaoImpl implements  UserDao{
    @Autowired
    UserRepository userRepository;

    @Override
    public User registerUser(RegisterDTO registerDTO) throws UserAlreadyExistsException {
        User currentUser= Convert.convertToEntity(registerDTO);
        userRepository.findByUserName(currentUser.getUserName())
                .ifPresent(error ->{
                    try {
                        throw new UserAlreadyExistsException();
                    } catch (UserAlreadyExistsException e) {
                        e.printStackTrace();
                    }
                });
        currentUser.setPassword(registerDTO.getPassword());
        return userRepository.save(currentUser);
    }

    @Override
    public String validateLogin(LoginDTO loginDTO) throws UserDoesNotExistException, WrongPasswordException {
        User currentUser= Convert.convertToEntity(loginDTO);
        currentUser.setPassword(loginDTO.getPassword());
        User userByUserName= userRepository.findByUserName(currentUser.getUserName())
                .orElseThrow(UserDoesNotExistException::new);
        if(userByUserName.getPassword().equals(currentUser.getPassword())){
            return userByUserName.getAccountName();
        }
        else{
            throw new WrongPasswordException();
        }
    }
}
