package com.kpi.journalrest.common;

import jakarta.ws.rs.core.Response.Status;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ErrorResponse {

    String message;
    Status status;

    public static ErrorResponse notFound(String message) {
        return new ErrorResponse("Could not find " + message, Status.NOT_FOUND);
    }

    public static ErrorResponse badRequest(String message) {
        return new ErrorResponse(message, Status.BAD_REQUEST);
    }

}
