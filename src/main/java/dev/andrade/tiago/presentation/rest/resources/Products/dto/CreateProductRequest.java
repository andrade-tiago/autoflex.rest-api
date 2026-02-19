package dev.andrade.tiago.presentation.rest.resources.Products.dto;

import java.math.BigDecimal;
import java.util.List;

import dev.andrade.tiago.application.dto.ProductCompositionItemInput;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record CreateProductRequest(
  @NotBlank
  String name,

  @NotNull
  @PositiveOrZero
  BigDecimal value,

  @NotNull
  List<ProductCompositionItemInput> composition
) {}
