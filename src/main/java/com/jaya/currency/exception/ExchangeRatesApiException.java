package com.jaya.currency.exception;

import org.springframework.http.HttpStatus;

public class ExchangeRatesApiException extends RuntimeException{

    private HttpStatus status;

    public ExchangeRatesApiException(String msg, HttpStatus status) {
        super(msg);
        this.status = status;
    }
}
