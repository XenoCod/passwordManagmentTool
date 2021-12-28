package com.epam.passwordmanagementrest.controller;

import com.epam.passwordmanagementrest.config.JWTTokenHelper;
import com.epam.passwordmanagementrest.dto.LoginDTO;
import com.epam.passwordmanagementrest.exception.UserDoesNotExistException;
import com.epam.passwordmanagementrest.exception.WrongPasswordException;
import com.epam.passwordmanagementrest.service.AccountServiceImpl;
import com.epam.passwordmanagementrest.service.GroupServiceImpl;
import com.epam.passwordmanagementrest.service.UserService;
import com.epam.passwordmanagementrest.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;


@RestController
@CrossOrigin
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    JWTTokenHelper jWTTokenHelper;

    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private UserService userService;

    @Autowired
    UserServiceImpl userServiceImpl;

    @Autowired
    GroupServiceImpl groupServiceImpl;

    @Autowired
    AccountServiceImpl accountServiceImpl;

    @PostMapping("/token")
    public ResponseEntity<?> generateToken(@RequestBody @Valid LoginDTO userDTO, HttpSession session)
            throws UsernameNotFoundException, UserDoesNotExistException, WrongPasswordException {
        this.authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userDTO.getUserName(), userDTO.getPassword()));

        UserDetails userDetails = this.userDetailsService.loadUserByUsername(userDTO.getUserName());
        String token = this.jWTTokenHelper.generateToken(userDetails);

        String accountName = userServiceImpl.validateLogin(userDTO);
        groupServiceImpl.setUser(userDTO);
        accountServiceImpl.setUser(userDTO);
        session.setAttribute("accountName", accountName);
        session.setAttribute("user", userDTO);
        userDTO.setToken(token);
        return ResponseEntity.ok(userDTO);
    }

}

