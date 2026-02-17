package dev.andrade.tiago.application.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record ProductOutput(
  UUID id,
  String name,
  BigDecimal value,
  List<ProductCompositionItemOutput> composition
) {}
