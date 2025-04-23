package com.BackEnd.BackEndHealthHabbits.infra.exceptions;

public class DataIntegrityCustomException extends RuntimeException {
    public DataIntegrityCustomException(String message) {
        super(message);
    }
}
