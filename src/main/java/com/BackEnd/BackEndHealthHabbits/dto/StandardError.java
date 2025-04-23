package com.BackEnd.BackEndHealthHabbits.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StandardError {
    private String timestamp;
    private int status;
    private String error;
    private String message;
    private String path;
}
