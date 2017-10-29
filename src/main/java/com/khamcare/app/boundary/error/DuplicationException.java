package com.khamcare.app.boundary.error;

public class DuplicationException extends RuntimeException{

    public DuplicationException(){
        super();
    }

    public DuplicationException(String message){
        super(message);
    }
}
