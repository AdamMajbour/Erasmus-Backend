package com.example.erasmus_app.Exceptions;

import org.springframework.http.HttpStatus;

public class AlreadyExistException extends RuntimeException{
    final HttpStatus httpStatus;

    public AlreadyExistException(HttpStatus httpStatus, String message){
        super(message);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
