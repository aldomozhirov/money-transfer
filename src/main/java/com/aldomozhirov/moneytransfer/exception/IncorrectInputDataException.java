package com.aldomozhirov.moneytransfer.exception;

import org.eclipse.jetty.http.HttpStatus;

public class IncorrectInputDataException extends CheckedException {

    private static final long serialVersionUID = 1L;

    public IncorrectInputDataException() {
        this("Incorrect data input");
    }

    public IncorrectInputDataException(String string) {
        super(string, HttpStatus.BAD_REQUEST_400);
    }

}


