package com.client.api.exception;

public class ValidationException extends RuntimeException{
    public ValidationException(){
    }

    public ValidationException(String message){
        super(message);
    }

    public ValidationException(ErrorStatus errorStatus){
        super(errorStatus.status);
    }
}
