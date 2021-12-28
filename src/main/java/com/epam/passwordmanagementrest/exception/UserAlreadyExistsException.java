package com.epam.passwordmanagementrest.exception;

public class UserAlreadyExistsException extends  Exception{
    public UserAlreadyExistsException(){
        super("This user already exists..Please Log in!!!");
    }
}
