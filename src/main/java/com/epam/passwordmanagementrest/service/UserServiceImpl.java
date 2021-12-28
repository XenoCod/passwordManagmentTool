package com.epam.passwordmanagementrest.service;

import com.epam.passwordmanagementrest.converter.Convert;
import com.epam.passwordmanagementrest.dao.UserDao;
import com.epam.passwordmanagementrest.dao.UserDaoImpl;
import com.epam.passwordmanagementrest.dto.LoginDTO;
import com.epam.passwordmanagementrest.dto.RegisterDTO;
import com.epam.passwordmanagementrest.dto.UserPrincipalDTO;
import com.epam.passwordmanagementrest.entity.AuthGroup;
import com.epam.passwordmanagementrest.entity.User;
import com.epam.passwordmanagementrest.exception.UserAlreadyExistsException;
import com.epam.passwordmanagementrest.exception.UserDoesNotExistException;
import com.epam.passwordmanagementrest.exception.WrongPasswordException;
import com.epam.passwordmanagementrest.repository.AuthGroupRepository;
import com.epam.passwordmanagementrest.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {
    @Autowired
    UserDaoImpl userDaoImpl;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthGroupRepository authGroupRepository;

    @Override
    public User registerUser(RegisterDTO registerDTO) throws UserAlreadyExistsException {
        return userDaoImpl.registerUser(registerDTO);
    }

    @Override
    public String validateLogin(LoginDTO loginDTO) throws UserDoesNotExistException, WrongPasswordException {
        return userDaoImpl.validateLogin(loginDTO);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = null;
        try {
            user = userRepository.findByUserName(username).orElseThrow(UserDoesNotExistException::new);
        } catch (UserDoesNotExistException e) {
            e.printStackTrace();
        }
        List<AuthGroup> authGroup=authGroupRepository.findByUsername(username);
        return new UserPrincipalDTO(user, authGroup);

    }
}
