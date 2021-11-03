package com.jaya.currency.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ClientException extends RuntimeException{

    private HttpStatus status;

    public ClientException(String msg, HttpStatus status) {
        super(msg);
        this.status = status;
    }
}
