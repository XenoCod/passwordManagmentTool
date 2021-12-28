package com.epam.passwordmanagementrest.exception;

import com.epam.passwordmanagementrest.entity.Group;

public class GroupShouldNotContainsAccounts extends Exception{
    public GroupShouldNotContainsAccounts(){
        super("Group should be empty before deleting");
    }
}
