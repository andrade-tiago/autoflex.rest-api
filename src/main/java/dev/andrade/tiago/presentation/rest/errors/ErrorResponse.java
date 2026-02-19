package dev.andrade.tiago.presentation.rest.errors;

import java.time.Instant;

public class ErrorResponse {
  public int status;
  public String message;
  public final Instant timestamp = Instant.now();
}
