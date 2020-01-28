package com.aldomozhirov.moneytransfer.exception;

import org.eclipse.jetty.http.HttpStatus;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class IncorrectInputDataException extends Exception implements ExceptionMapper<IncorrectInputDataException> {

    private static final long serialVersionUID = 1L;

    public IncorrectInputDataException() {
        super("Incorrect data input");
    }

    public IncorrectInputDataException(String string) {
        super(string);
    }

    @Override
    public Response toResponse(IncorrectInputDataException exception) {
        return Response
                .status(HttpStatus.BAD_REQUEST_400)
                .entity(exception.getMessage())
                .type("text/plain").build();
    }

}


