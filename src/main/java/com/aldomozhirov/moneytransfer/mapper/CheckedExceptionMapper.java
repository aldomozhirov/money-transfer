package com.aldomozhirov.moneytransfer.mapper;

import com.aldomozhirov.moneytransfer.exception.CheckedException;
import org.eclipse.jetty.http.HttpStatus;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class CheckedExceptionMapper implements ExceptionMapper<CheckedException> {

    public CheckedExceptionMapper() {}

    @Override
    public Response toResponse(CheckedException exception) {
        return Response
                .status(exception.getStatus())
                .entity(exception.getMessage())
                .type("text/plain").build();
    }

}
