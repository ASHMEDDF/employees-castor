package com.nn.castor.exception;

import java.io.Serial;

public class EmployeeAlreadyExistsException extends RuntimeException{

    @Serial
    private static final long serialVersionUID = 2L;

    public EmployeeAlreadyExistsException(String id) {
        super("Employee with ID " + id + " already exists");
    }
}
