package dev.andrade.tiago.presentation.rest.interceptors;

import dev.andrade.tiago.application.exceptions.ResourceNotFoundException;
import dev.andrade.tiago.presentation.rest.errors.NotFoundErrorResponse;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class ResourceNotFoundMapper implements ExceptionMapper<ResourceNotFoundException> {
  @Override
  public Response toResponse(ResourceNotFoundException exception) {
    var error = new NotFoundErrorResponse();
    error.message = exception.getMessage();
    error.ids = exception.getIDs();

    return Response.status(Status.NOT_FOUND)
      .entity(error)
      .build();
  }
}
