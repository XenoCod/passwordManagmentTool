package com.epam.passwordmanagementrest.exception;

public class UnableToAddAccount extends Exception{
    public UnableToAddAccount(){
        super("Unable to add account..Try again later");
    }
}
