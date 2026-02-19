package dev.andrade.tiago.presentation.rest.resources.Production.dto;

import java.math.BigDecimal;
import java.util.List;

public record SuggestProductionResponse(
  BigDecimal totalValue,
  List<SuggestProductionResponseItem> products
) {}
