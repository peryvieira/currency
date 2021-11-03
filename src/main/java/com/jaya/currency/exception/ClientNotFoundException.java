package com.jaya.currency.exception;

import lombok.NonNull;

public class ClientNotFoundException extends RuntimeException{
    public ClientNotFoundException(@NonNull Long id) {
        super("Client with id = " + id + " cannot be found in the database.");
    }
}
