package dev.andrade.tiago.application.dto;

import java.util.UUID;

public record ProductCompositionItemOutput(
  UUID materialId,
  int quantity
) {}
