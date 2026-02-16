package dev.andrade.tiago.application.usecases.SuggestProduction;

import java.math.BigDecimal;
import java.util.UUID;

public record SuggestProductionOutputItem(
  UUID productId,
  String productName,
  int quantity,
  BigDecimal unitValue,
  BigDecimal totalValue
) {}
