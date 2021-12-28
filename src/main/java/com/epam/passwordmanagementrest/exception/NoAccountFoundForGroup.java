package com.epam.passwordmanagementrest.exception;

public class NoAccountFoundForGroup extends Exception{
    public NoAccountFoundForGroup(){
        super("No account exists for this group");
    }
}
