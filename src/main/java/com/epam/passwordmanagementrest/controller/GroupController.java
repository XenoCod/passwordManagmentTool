package com.epam.passwordmanagementrest.controller;

import com.epam.passwordmanagementrest.dto.GroupDTO;
import com.epam.passwordmanagementrest.entity.Account;
import com.epam.passwordmanagementrest.entity.Group;
import com.epam.passwordmanagementrest.exception.GroupShouldNotContainsAccounts;
import com.epam.passwordmanagementrest.exception.NoAccountFoundForGroup;
import com.epam.passwordmanagementrest.exception.NoGroupFoundForUser;
import com.epam.passwordmanagementrest.service.GroupServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = {"", "api/group"})
public class GroupController {
    @Autowired
    GroupServiceImpl groupServiceImpl;

    private static final String GROUP_ADDED = "Group added successfully!!";
    private static final String GROUP_UPDATED = "Group updated successfully!!";
    private static final String GROUP_DELETED = "Group deleted successfully!!";

    @GetMapping(value = "/viewGroups")
    public ResponseEntity<List<Group>> getGroupList() throws NoGroupFoundForUser {
        List<Group> groupList = groupServiceImpl.findAllGroups();
        return new ResponseEntity<>(groupList, HttpStatus.OK);
    }

    @PostMapping(value = "/addGroup")
    public ResponseEntity<Object> addGroup(@Valid @RequestBody GroupDTO groupDTO) {
        Group addedGroup = groupServiceImpl.addGroup(groupDTO);
        return new ResponseEntity<>(GROUP_ADDED + " " + addedGroup, HttpStatus.OK);
    }

    @GetMapping(value = "findAccountByGroup/{name}")
    public ResponseEntity<List<Account>> getRecordByGroup(@PathVariable String name) throws NoGroupFoundForUser, NoAccountFoundForGroup {
        List<Account> accountList = groupServiceImpl.findAllAccountByGroupName(name);
        return new ResponseEntity<>(accountList, HttpStatus.OK);
    }

    @GetMapping(value = "showEditGroupForm/{name}")
    public ResponseEntity<Object> getEditGroupForm(@PathVariable String name, HttpSession session) throws NoGroupFoundForUser {
        Group fetchGroup = groupServiceImpl.findGroupByName(name);
        session.setAttribute("group_id", fetchGroup.getId());
        return new ResponseEntity<>(fetchGroup, HttpStatus.OK);
    }

    @PutMapping(value = "/updateGroup/{groupId}")
    public ResponseEntity<Object> updateGroup(@PathVariable int groupId, @RequestBody @Valid GroupDTO groupDTO) throws NoGroupFoundForUser {
        groupDTO.setId(groupId);
        Group updatedGroup = groupServiceImpl.updateGroup(groupDTO);
        return new ResponseEntity<>(GROUP_UPDATED + " " + updatedGroup, HttpStatus.OK);
    }

    @DeleteMapping(value = "/deleteGroup/{groupName}")
    public ResponseEntity<Object> deleteGroup(@PathVariable String groupName) throws GroupShouldNotContainsAccounts, NoGroupFoundForUser {
        Group deletedGroup = groupServiceImpl.deleteGroup(groupName);
        return new ResponseEntity<>(GROUP_DELETED + " " + deletedGroup, HttpStatus.OK);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAllException(Exception ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_ACCEPTABLE);
    }
}
