package com.BackEnd.BackEndHealthHabbits.infra.exceptions;

public class SamePasswordException extends RuntimeException {
    public SamePasswordException(String message) {
        super(message);
    }
}
