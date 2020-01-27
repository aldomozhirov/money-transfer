package com.aldomozhirov.moneytransfer.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class NotEnoughMoneyException extends Exception implements ExceptionMapper<NotEnoughMoneyException> {

    private static final long serialVersionUID = 1L;

    public NotEnoughMoneyException() {
        super("Not enough money error");
    }

    public NotEnoughMoneyException(String string) {
        super(string);
    }

    @Override
    public Response toResponse(NotEnoughMoneyException exception)
    {
        return Response.status(404).entity(exception.getMessage())
                .type("text/plain").build();
    }

}
