package com.BackEnd.BackEndHealthHabbits.infra.exceptions;

public class AlreadyExists extends RuntimeException {
    public AlreadyExists(String message) {
        super(message);
    }
}
