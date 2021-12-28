package com.epam.passwordmanagementrest.exception;

public class AccountAlreadyExistsException extends Exception{
    public AccountAlreadyExistsException(){
        super("This account already exists");
    }
}
