package com.client.api.exception;

public class ClientNotFoundException extends RuntimeException{
    public ClientNotFoundException(){
    }

    public ClientNotFoundException(String message){
        super(message);
    }

    public ClientNotFoundException(ErrorStatus errorStatus){
        super(errorStatus.status);
    }
}