package dev.andrade.tiago.presentation.rest.dto;

import java.math.BigDecimal;
import java.util.List;

public record SuggestProductionResponse(
  BigDecimal totalValue,
  List<SuggestProductionResponseItem> products
) {}
