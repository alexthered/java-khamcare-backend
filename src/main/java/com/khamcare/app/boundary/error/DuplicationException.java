package com.khamcare.app.boundary.error;

public class DuplicationException extends RuntimeException{

    public DuplicationException(String message){
        super(message);
    }
}
