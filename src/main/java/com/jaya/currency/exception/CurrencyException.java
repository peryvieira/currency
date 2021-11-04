package com.jaya.currency.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CurrencyException extends RuntimeException {

    private HttpStatus status;

    public CurrencyException(String msg, HttpStatus status) {
        super(msg);
        this.status = status;
    }
}
