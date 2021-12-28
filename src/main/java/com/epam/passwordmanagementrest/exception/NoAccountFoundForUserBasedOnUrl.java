package com.epam.passwordmanagementrest.exception;

public class NoAccountFoundForUserBasedOnUrl extends Exception{
    public NoAccountFoundForUserBasedOnUrl(){
        super("No accounts exists");
    }
}
