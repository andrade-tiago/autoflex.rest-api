package dev.andrade.tiago.presentation.rest.Products.dto;

import java.util.UUID;

import jakarta.validation.constraints.Positive;

public record ProductCompositionItemRequest(
  UUID rawMaterialId,
  
  @Positive
  int requiredQuantity
) {}
