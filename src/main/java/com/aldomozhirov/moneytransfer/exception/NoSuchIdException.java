package com.aldomozhirov.moneytransfer.exception;

import org.eclipse.jetty.http.HttpStatus;

public class NoSuchIdException extends CheckedException {

    private static final long serialVersionUID = 1L;

    public NoSuchIdException() {
        this("No such id error");
    }

    public NoSuchIdException(String string) {
        super(string, HttpStatus.BAD_REQUEST_400);
    }

}
