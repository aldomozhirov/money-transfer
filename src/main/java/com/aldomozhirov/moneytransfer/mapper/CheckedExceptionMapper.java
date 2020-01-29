package com.aldomozhirov.moneytransfer.mapper;

import com.aldomozhirov.moneytransfer.dto.Error;
import com.aldomozhirov.moneytransfer.exception.CheckedException;

import javax.ws.rs.core.MediaType;
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
                .entity(new Error(exception.getStatus(), exception.getMessage()))
                .type(MediaType.APPLICATION_JSON).build();
    }

}
