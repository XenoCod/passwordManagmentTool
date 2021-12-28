package com.epam.passwordmanagementrest.converter;

import com.epam.passwordmanagementrest.dto.AccountDTO;
import com.epam.passwordmanagementrest.dto.GroupDTO;
import com.epam.passwordmanagementrest.dto.LoginDTO;
import com.epam.passwordmanagementrest.dto.RegisterDTO;
import com.epam.passwordmanagementrest.entity.Account;
import com.epam.passwordmanagementrest.entity.Group;
import com.epam.passwordmanagementrest.entity.User;
import org.springframework.stereotype.Component;

@Component
public class Convert {
    public static User convertToEntity(RegisterDTO registerDTO) {
        return new User(registerDTO.getAccountName(), registerDTO.getUserName(), registerDTO.getPassword());
    }

    public static User convertToEntity(LoginDTO loginDTO) {
        return new User(loginDTO.getUserName(), loginDTO.getPassword());
    }

    public static Account convertToEntity(AccountDTO accountDTO) {
        return new Account(accountDTO.getUserName(), accountDTO.getPassword(), accountDTO.getUrl(), accountDTO.getMessage());
    }

    public static Group convertToEntity(GroupDTO groupDTO) {
        return new Group(groupDTO.getId(), groupDTO.getName());
    }
}
