package com.epam.passwordmanagementrest.exception;

public class GroupAlreadyExistsException extends Exception{
    public GroupAlreadyExistsException(){
        super("This Group name already exists!! Try with a different Name");
    }
}
