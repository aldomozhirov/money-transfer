package com.aldomozhirov.moneytransfer.exception;

import org.eclipse.jetty.http.HttpStatus;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class RepositoryException extends Exception implements ExceptionMapper<RepositoryException> {

    private static final long serialVersionUID = 1L;

    public RepositoryException() {
        super("Data repository error");
    }

    public RepositoryException(String string) {
        super(string);
    }

    @Override
    public Response toResponse(RepositoryException exception)
    {
        return Response
                .status(HttpStatus.BAD_REQUEST_400)
                .entity(exception.getMessage())
                .type("text/plain").build();
    }

}
