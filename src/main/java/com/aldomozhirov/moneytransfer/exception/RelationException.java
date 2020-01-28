package com.aldomozhirov.moneytransfer.exception;

public class RelationException extends CheckedException {

    private static final long serialVersionUID = 1L;

    public RelationException() {
        this("Relation dependency error");
    }

    public RelationException(String string) {
        super(string);
    }

}
