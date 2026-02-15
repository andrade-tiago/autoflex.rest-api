package dev.andrade.tiago.presentation.rest.dto;

import java.math.BigDecimal;
import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.PositiveOrZero;

public record CreateProductRequest(
  @NotBlank
  String name,

  @PositiveOrZero
  BigDecimal value,

  @NotEmpty
  List<ProductCompositionItemRequest> composition
) {}
