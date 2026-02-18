package dev.andrade.tiago.presentation.rest.Products.dto;

import java.math.BigDecimal;
import java.util.List;

import dev.andrade.tiago.application.dto.ProductCompositionItemInput;

public record UpdateProductRequest(
  String name,
  BigDecimal value,
  List<ProductCompositionItemInput> composition
) {}
