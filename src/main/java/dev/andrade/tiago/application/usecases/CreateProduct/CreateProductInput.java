package dev.andrade.tiago.application.usecases.CreateProduct;

import java.math.BigDecimal;
import java.util.List;

import dev.andrade.tiago.application.dto.ProductCompositionItemInput;

public record CreateProductInput(
  String name,
  BigDecimal value,
  List<ProductCompositionItemInput> composition
) {}
