package com.aldomozhirov.moneytransfer.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class NoSuchIdException extends Exception implements ExceptionMapper<NoSuchIdException> {

    private static final long serialVersionUID = 1L;

    public NoSuchIdException() {
        super("No such id error");
    }

    public NoSuchIdException(String string) {
        super(string);
    }

    @Override
    public Response toResponse(NoSuchIdException exception)
    {
        return Response.status(400).entity(exception.getMessage())
                .type("text/plain").build();
    }

}
