package com.BackEnd.BackEndHealthHabbits.infra.exceptions;

import java.util.List;

public class MultipleValidationException extends Exception{
    private List<String> errorMessages;

    public MultipleValidationException(List<String> errorMessages) {
        super("Múltiplos erros de validação ocorreram.");
        this.errorMessages = errorMessages;
    }

    public List<String> getErrorMessages() {
        return errorMessages;
    }
}
