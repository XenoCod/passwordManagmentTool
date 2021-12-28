package com.epam.passwordmanagementrest.service;

import com.epam.passwordmanagementrest.converter.Convert;
import com.epam.passwordmanagementrest.dao.GroupDaoImpl;
import com.epam.passwordmanagementrest.dto.GroupDTO;
import com.epam.passwordmanagementrest.dto.LoginDTO;
import com.epam.passwordmanagementrest.entity.Account;
import com.epam.passwordmanagementrest.entity.Group;
import com.epam.passwordmanagementrest.entity.User;
import com.epam.passwordmanagementrest.exception.GroupAlreadyExistsException;
import com.epam.passwordmanagementrest.exception.GroupShouldNotContainsAccounts;
import com.epam.passwordmanagementrest.exception.NoAccountFoundForGroup;
import com.epam.passwordmanagementrest.exception.NoGroupFoundForUser;
import com.epam.passwordmanagementrest.repository.AccountRepository;
import com.epam.passwordmanagementrest.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupServiceImpl implements GroupService {
   @Autowired
    GroupDaoImpl groupDaoImpl;



    @Override
    public Group addGroup(GroupDTO groupDTO) {
        return groupDaoImpl.addGroup(groupDTO);
    }

    @Override
    public List<Account> findAllAccountByGroupName(String name) throws NoAccountFoundForGroup, NoGroupFoundForUser {
        return groupDaoImpl.findAllAccountByGroupName(name);
    }

    @Override
    public Group updateGroup(GroupDTO groupDTO) throws NoGroupFoundForUser {
        return groupDaoImpl.updateGroup(groupDTO);
    }

    @Override
    public List<Group> findAllGroups() throws NoGroupFoundForUser {
       return groupDaoImpl.findAllGroups();
    }

    @Override
    public Group deleteGroup(String name) throws GroupShouldNotContainsAccounts, NoGroupFoundForUser {
       return groupDaoImpl.deleteGroup(name);
    }

    @Override
    public void setUser(LoginDTO loginDTO) {
        groupDaoImpl.setUser(loginDTO);
    }

    @Override
    public Group findGroupByName(String name) throws NoGroupFoundForUser {
        return groupDaoImpl.findGroupByName(name);
    }

    @Override
    public User getUser() {
        return groupDaoImpl.getUser();
    }

    public List<Account> findAllAccountsByGroupName(String name) throws NoGroupFoundForUser, NoAccountFoundForGroup {
        return groupDaoImpl.findAllAccountByGroupName(name);
    }
}
