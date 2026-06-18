package com.Simran.apartmentmanager.exception;

public class BadRequestException extends RuntimeException{

    public BadRequestException(String message)
    {
        super(message);
    }
}
