package dev.andrade.tiago.presentation.rest.errors;

import java.util.List;
import java.util.UUID;

import org.jboss.resteasy.reactive.RestResponse.StatusCode;

public class NotFoundErrorResponse extends ErrorResponse {
  public final int status = StatusCode.NOT_FOUND;

  public List<UUID> ids;
}
