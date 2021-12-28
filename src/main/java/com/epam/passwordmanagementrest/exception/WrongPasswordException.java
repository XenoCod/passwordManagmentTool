package com.epam.passwordmanagementrest.exception;

public class WrongPasswordException extends Exception{
    public WrongPasswordException(){
        super("Wrong password.!!");
    }
}
