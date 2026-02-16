package dev.andrade.tiago.application.usecases.SuggestProduction;

import java.math.BigDecimal;
import java.util.List;

public record SuggestProductionOutput(
  BigDecimal totalValue,
  List<SuggestProductionOutputItem> products
) {}


