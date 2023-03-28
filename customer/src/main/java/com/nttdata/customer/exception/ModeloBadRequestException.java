package com.nttdata.customer.exception;

public class ModeloBadRequestException extends RuntimeException{
    public ModeloBadRequestException(String mensaje){
        super(mensaje);
    }
}
