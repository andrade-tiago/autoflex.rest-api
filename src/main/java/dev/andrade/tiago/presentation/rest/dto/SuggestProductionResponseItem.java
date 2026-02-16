package dev.andrade.tiago.presentation.rest.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record SuggestProductionResponseItem (
  UUID productId,
  String productName,
  int quantity,
  BigDecimal unitValue,
  BigDecimal totalValue
) {}
