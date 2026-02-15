package dev.andrade.tiago.application.exceptions;

import java.util.List;
import java.util.UUID;

public class ResourceNotFoundException extends RuntimeException {
  private List<UUID> ids;

  public ResourceNotFoundException(String message, List<UUID> ids) {
    super(message);
    this.ids = ids;
  }

  public List<UUID> getIDs() {
    return List.copyOf(this.ids);
  }
}
