package com.uma.wiki.exception;

public class InvalidVersionException extends RuntimeException{
    public InvalidVersionException (String message){
        super(message);
    }
}
