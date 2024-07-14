package com.nn.castor.exception;


import java.io.Serial;

public class PositionNotFoundException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public PositionNotFoundException(Long id) {
        super("Position with ID " + id + " not found");
    }
}
