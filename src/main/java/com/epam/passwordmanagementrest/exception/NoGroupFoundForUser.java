package com.epam.passwordmanagementrest.exception;

public class NoGroupFoundForUser extends Exception{
    public NoGroupFoundForUser(){
        super("No Group exsits!!");
    }
}
