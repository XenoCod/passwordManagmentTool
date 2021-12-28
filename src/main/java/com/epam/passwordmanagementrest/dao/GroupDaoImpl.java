package com.epam.passwordmanagementrest.dao;

import com.epam.passwordmanagementrest.converter.Convert;
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

import java.util.List;

@Component
public class GroupDaoImpl implements GroupDao{
    @Autowired
    GroupRepository groupRepository;
    @Autowired
    AccountRepository accountRepository;
    private User user;


    @Override
    public Group addGroup(GroupDTO groupDTO) {

        Group group = Convert.convertToEntity(groupDTO);
        group.setUser(user);
        groupRepository.findByNameAndUser(group.getName(), group.getUser())
                .ifPresent(error -> {
                    try {
                        throw new GroupAlreadyExistsException();
                    } catch (GroupAlreadyExistsException e) {
                        e.printStackTrace();
                    }
                });
        return groupRepository.save(group);
    }

    @Override
    public List<Account> findAllAccountByGroupName(String name) throws NoAccountFoundForGroup, NoGroupFoundForUser {
        Group existGroup = findGroupByName(name);
        List<Account> accountList = accountRepository.findByGroupAndUser(existGroup, user);
        if (accountList.isEmpty()) {
            throw new NoAccountFoundForGroup();
        }
        return accountList;
    }

    @Override
    public Group updateGroup(GroupDTO groupDTO) throws NoGroupFoundForUser {
        Group group = Convert.convertToEntity(groupDTO);
        group.setUser(user);
        Group fetchGroup = groupRepository.findById(group.getId())
                .orElseThrow(NoGroupFoundForUser::new);
        groupRepository.findByNameAndUser(groupDTO.getName(), group.getUser())
                .ifPresent(error -> {
                    try {
                        throw new GroupAlreadyExistsException();
                    } catch (GroupAlreadyExistsException e) {
                        e.printStackTrace();
                    }
                });
        fetchGroup.setName(group.getName());
        return groupRepository.save(fetchGroup);
    }

    @Override
    public List<Group> findAllGroups() throws NoGroupFoundForUser {
        List<Group> groupList = groupRepository.findByUser(user);
        if (groupList.isEmpty()) {
            throw new NoGroupFoundForUser();
        }
        return groupList;
    }

    @Override
    public Group deleteGroup(String name) throws GroupShouldNotContainsAccounts, NoGroupFoundForUser {
        Group existGroup = groupRepository.findByNameAndUser(name, user)
                .orElseThrow(NoGroupFoundForUser::new);
        List<Account> accountList= accountRepository.findByGroupAndUser(existGroup, user);
        if (accountList.isEmpty()) {
            groupRepository.deleteById(existGroup.getId());
            return existGroup;
        } else {
            throw new GroupShouldNotContainsAccounts();
        }
    }

    @Override
    public void setUser(LoginDTO loginDTO) {
        this.user = Convert.convertToEntity(loginDTO);
    }

    @Override
    public Group findGroupByName(String name) throws NoGroupFoundForUser {
        return groupRepository.findByNameAndUser(name, user)
                .orElseThrow(NoGroupFoundForUser::new);
    }

    @Override
    public User getUser() {
        return this.user;
    }
}
