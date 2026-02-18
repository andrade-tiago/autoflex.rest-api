package dev.andrade.tiago.application.dto;

import java.util.UUID;

public record ProductCompositionItemInput(
  UUID rawMaterialId,
  int requiredQuantity
) {}
