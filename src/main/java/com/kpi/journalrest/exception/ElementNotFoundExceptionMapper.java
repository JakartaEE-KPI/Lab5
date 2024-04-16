package com.kpi.journalrest.exception;

import com.kpi.journalrest.common.ErrorResponse;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import static jakarta.ws.rs.core.Response.Status.NOT_FOUND;

@Provider
public class ElementNotFoundExceptionMapper implements ExceptionMapper<ElementNotFoundException> {

    @Override
    public Response toResponse(ElementNotFoundException exception) {
        return Response.status(NOT_FOUND)
                .entity(ErrorResponse.notFound(exception.getMessage()))
                .type(MediaType.APPLICATION_JSON)
                .build();
    }

}
