package com.epam.passwordmanagementrest.exception;

public class UserDoesNotExistException extends Exception{
    public UserDoesNotExistException(){
        super("This user does not exists..Please create a user");
    }
}
