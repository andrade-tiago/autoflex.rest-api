package dev.andrade.tiago.presentation.rest.interceptors;

import org.jboss.resteasy.reactive.RestResponse.StatusCode;

import dev.andrade.tiago.presentation.rest.errors.ErrorResponse;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class GlobalExceptionMapper implements ExceptionMapper<Throwable> {
  @Override
  public Response toResponse(Throwable exception) {
    var response = new ErrorResponse();
    response.message = "Internal server error";
    response.status = StatusCode.INTERNAL_SERVER_ERROR;

    return Response.serverError()
      .entity(response)
      .build();
  }
}
