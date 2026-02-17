package dev.andrade.tiago.presentation.rest.Production.dto;

import java.math.BigDecimal;
import java.util.List;

public record SuggestProductionResponse(
  BigDecimal totalValue,
  List<SuggestProductionResponseItem> products
) {}
