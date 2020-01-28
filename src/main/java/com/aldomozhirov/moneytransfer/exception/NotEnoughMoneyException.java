package com.aldomozhirov.moneytransfer.exception;

import org.eclipse.jetty.http.HttpStatus;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
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
        return Response
                .status(HttpStatus.BAD_REQUEST_400)
                .entity(exception.getMessage())
                .type("text/plain").build();
    }

}
