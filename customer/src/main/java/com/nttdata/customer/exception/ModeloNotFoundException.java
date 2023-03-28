package com.nttdata.customer.exception;

public class ModeloNotFoundException extends RuntimeException{
    public ModeloNotFoundException(String message) {
        super(message);
    }
}
