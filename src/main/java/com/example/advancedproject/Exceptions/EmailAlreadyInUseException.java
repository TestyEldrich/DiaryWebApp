package com.example.advancedproject.Exceptions;

public class EmailAlreadyInUseException extends Exception{
    public EmailAlreadyInUseException(String message){
        super(message);
    }
}
