package com.epam.passwordmanagementrest.exception;

public class NoAccountFoundForUser extends Exception{
    public NoAccountFoundForUser(){
        super("No account for this user");
    }
}
