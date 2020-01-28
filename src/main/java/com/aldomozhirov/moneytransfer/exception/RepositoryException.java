package com.aldomozhirov.moneytransfer.exception;

import org.eclipse.jetty.http.HttpStatus;

public class RepositoryException extends CheckedException {

    private static final long serialVersionUID = 1L;

    public RepositoryException() {
        this("Data repository error");
    }

    public RepositoryException(String string) {
        super(string);
    }

}
