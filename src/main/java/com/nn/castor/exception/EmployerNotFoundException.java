package com.nn.castor.exception;


import java.io.Serial;

public class EmployerNotFoundException extends RuntimeException{

    @Serial
    private static final long serialVersionUID = 1L;

    public EmployerNotFoundException(Long id) {
        super("Employee with ID " + id + " not found");
    }
}
