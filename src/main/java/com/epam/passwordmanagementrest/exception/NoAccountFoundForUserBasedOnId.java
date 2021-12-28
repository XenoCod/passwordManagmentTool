package com.epam.passwordmanagementrest.exception;

public class NoAccountFoundForUserBasedOnId extends Exception{
    public NoAccountFoundForUserBasedOnId(){
        super("No account found");
    }
}
