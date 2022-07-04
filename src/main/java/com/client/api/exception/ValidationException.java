package com.client.api.exception;

public class ValidationException extends RuntimeException{
    public ValidationException(){
    }

    public ValidationException(String message){
        super(message);
    }

    public ValidationException(ErrorCode errorCode){
        super(errorCode.status);
    }
}
