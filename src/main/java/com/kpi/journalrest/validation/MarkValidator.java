package com.kpi.journalrest.validation;

import com.kpi.journalrest.common.ErrorResponse;
import com.kpi.journalrest.dto.MarkRequest;
import jakarta.ejb.Stateless;
import jakarta.ws.rs.core.Response;

import static jakarta.ws.rs.core.Response.Status.BAD_REQUEST;

@Stateless
public class MarkValidator {
    public Response validateMarkFields(MarkRequest markRequest) {
        Integer point = markRequest.getPoint();
        if (point != null) {
            if (point < 0 || point > 100) {
                return Response.status(BAD_REQUEST)
                        .entity(ErrorResponse.badRequest("Point should be more than 0 and less than 100"))
                        .build();
            }
        }
        return null;
    }
}
