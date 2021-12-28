package com.epam.passwordmanagementrest.service;

import com.epam.passwordmanagementrest.dto.GroupDTO;
import com.epam.passwordmanagementrest.dto.LoginDTO;
import com.epam.passwordmanagementrest.entity.Account;
import com.epam.passwordmanagementrest.entity.Group;
import com.epam.passwordmanagementrest.entity.User;
import com.epam.passwordmanagementrest.exception.GroupShouldNotContainsAccounts;
import com.epam.passwordmanagementrest.exception.NoAccountFoundForGroup;
import com.epam.passwordmanagementrest.exception.NoGroupFoundForUser;

import java.util.List;

public interface GroupService {
    Group addGroup(GroupDTO groupDTO);

    List<Account> findAllAccountByGroupName(String name) throws NoAccountFoundForGroup, NoGroupFoundForUser;

    Group updateGroup(GroupDTO groupDTO) throws NoGroupFoundForUser;

    List<Group> findAllGroups() throws NoGroupFoundForUser;

    Group deleteGroup(String name) throws GroupShouldNotContainsAccounts, NoGroupFoundForUser;

    void setUser(LoginDTO loginDTO);

    Group findGroupByName(String name) throws NoGroupFoundForUser;

    User getUser();
}
