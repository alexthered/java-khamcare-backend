package com.khamcare.app.boundary.error;

public class BadArgumentException extends RuntimeException{

    public BadArgumentException(){
        super();
    }

    public BadArgumentException(String message){
        super(message);
    }
}
