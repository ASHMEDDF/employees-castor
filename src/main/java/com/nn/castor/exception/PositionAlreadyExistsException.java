package com.nn.castor.exception;

import java.io.Serial;

public class PositionAlreadyExistsException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public PositionAlreadyExistsException(String name) {
        super("Position with name " + name + " already exists");
    }
}
