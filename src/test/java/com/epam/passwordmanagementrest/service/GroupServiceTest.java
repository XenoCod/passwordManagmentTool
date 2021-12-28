package com.epam.passwordmanagementrest.service;

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
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GroupServiceTest {
    @InjectMocks
    GroupDaoImpl groupDaoImpl;
    @Mock
    GroupRepository groupRepository;
    @Mock
    AccountRepository accountRepository;

    @Test
    void testAddGroupThrowExceptionWhenGroupAlreadyExists() {
        GroupDTO groupDTO = new GroupDTO("Google");
        when(groupRepository.findByNameAndUser(any(), any())).thenReturn(Optional.of(new Group()));
        Assertions.assertThrows(GroupAlreadyExistsException.class, () -> groupDaoImpl.addGroup(groupDTO));
    }

    @Test
    void testAddGroupDoesNotThrowExceptionWhileInsertingNewGroup() {
        GroupDTO groupDTO = new GroupDTO("Google");
        Group addedGroup = new Group();
        when(groupRepository.save(any())).thenReturn(addedGroup);
        when(groupRepository.findByNameAndUser(any(), any())).thenReturn(Optional.empty());
        Assertions.assertDoesNotThrow(() -> groupDaoImpl.addGroup(groupDTO));
    }

    @Test
    void testFindAllAccountByGroupNameThrowExceptionWhenNoAccountExistForSearchedGroupName() {
        when(accountRepository.findByGroupAndUser(any(), any())).thenReturn(Collections.emptyList());
        when(groupRepository.findByNameAndUser(any(), any())).thenReturn(Optional.of(new Group()));
        Assertions.assertThrows(NoAccountFoundForGroup.class, () -> groupDaoImpl.findAllAccountByGroupName("Google"));
    }

    @Test
    void testFindAllAccountByGroupNameDoesNotThrowExceptionWhileAccountsFound() {
        List<Account> accountList = List.of(new Account("USER12", "Aditya19", "htt://www.master.com", "data123"));
        when(accountRepository.findByGroupAndUser(any(), any())).thenReturn(accountList);
        when(groupRepository.findByNameAndUser(any(), any())).thenReturn(Optional.of(new Group()));
        Assertions.assertEquals(accountList, Assertions.assertDoesNotThrow(() -> groupDaoImpl.findAllAccountByGroupName("Google")));
    }

    @Test
    void testUpdateGroupThrowExceptionWhenNoGroupExist() {
        GroupDTO groupDTO = new GroupDTO("Google");
        when(groupRepository.findById(any())).thenReturn(Optional.empty());
        Assertions.assertThrows(NoGroupFoundForUser.class, () -> groupDaoImpl.updateGroup(groupDTO));
    }

    @Test
    void testUpdateGroupThrowExceptionWhenGroupAlreadyExists() {
        GroupDTO groupDTO = new GroupDTO("Google");
        when(groupRepository.findByNameAndUser(any(), any())).thenReturn(Optional.of(new Group()));
        when(groupRepository.findById(any())).thenReturn(Optional.of(new Group()));
        Assertions.assertThrows(GroupAlreadyExistsException.class, () -> groupDaoImpl.updateGroup(groupDTO));
    }

    @Test
    void testUpdateGroupDoesNotThrowExceptionWhileUpdatingGroupSuccessfully() {
        GroupDTO groupDTO = new GroupDTO("Google");
        Group fetchGroup = new Group();
        when(groupRepository.save(any())).thenReturn(fetchGroup);
        when(groupRepository.findByNameAndUser(any(), any())).thenReturn(Optional.empty());
        when(groupRepository.findById(any())).thenReturn(Optional.of(new Group()));
        Assertions.assertDoesNotThrow(() -> groupDaoImpl.updateGroup(groupDTO));
    }

    @Test
    void testFindAllGroupThrowExceptionWhenNoGroupExist() {
        when(groupRepository.findByUser(any())).thenReturn(Collections.emptyList());
        Assertions.assertThrows(NoGroupFoundForUser.class, () -> groupDaoImpl.findAllGroups());
    }

    @Test
    void testFindAllGroupDoesNotThrowExceptionWhileGroupExist() {
        List<Group> groupList = List.of(new Group(1, "Google"));
        when(groupRepository.findByUser(any())).thenReturn(groupList);
        Assertions.assertDoesNotThrow(() -> groupDaoImpl.findAllGroups());
    }

    @Test
    void testFindGroupByNameThrowExceptionWhenNoGroupExist() {
        when(groupRepository.findByNameAndUser(any(), any())).thenReturn(Optional.empty());
        Assertions.assertThrows(NoGroupFoundForUser.class, () -> groupDaoImpl.findGroupByName("Google"));
    }

    @Test
    void testFindGroupByNameDoesNotThrowExceptionWhileGroupExist() {
        when(groupRepository.findByNameAndUser(any(), any())).thenReturn(Optional.of(new Group()));
        Assertions.assertDoesNotThrow(() -> groupDaoImpl.findGroupByName("Google"));
    }

    @Test
    void testDeleteGroupThrowExceptionWhenGroupHasRecords() {
        when(groupRepository.findByNameAndUser(any(), any())).thenReturn(Optional.ofNullable(null));
        Assertions.assertThrows(NoGroupFoundForUser.class, () -> groupDaoImpl.deleteGroup("Google"));
    }

    @Test
    void testDeleteGroupThrowExceptionWhenNoGroupExistsWithRecords() {
        List<Account> accountList = List.of(new Account("USER12", "Aditya19", "http://www.google.com", "data group"));
        when(accountRepository.findByGroupAndUser(any(), any())).thenReturn(accountList);
        when(groupRepository.findByNameAndUser(any(), any())).thenReturn(Optional.of(new Group()));
        Assertions.assertThrows(GroupShouldNotContainsAccounts.class, () -> groupDaoImpl.deleteGroup("Google"));
    }

    @Test
    void testDeleteGroupDoesNotThrowExceptionWhileDeletingGroupWithoutRecords() {
        when(accountRepository.findByGroupAndUser(any(), any())).thenReturn(Collections.emptyList());
        when(groupRepository.findByNameAndUser(any(), any())).thenReturn(Optional.of(new Group()));
        Assertions.assertDoesNotThrow(() -> groupDaoImpl.deleteGroup("Google"));
    }

    @Test
    void testAccount() {
        LoginDTO loginDTO = new LoginDTO("USER12", "Aditya19");
        groupDaoImpl.setUser(loginDTO);
        User user = new User("USER12", "Aditya19");
        Assertions.assertEquals(user.getUserName(), groupDaoImpl.getUser().getUserName());
    }
}