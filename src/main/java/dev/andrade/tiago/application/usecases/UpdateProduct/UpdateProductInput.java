package dev.andrade.tiago.application.usecases.UpdateProduct;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import dev.andrade.tiago.application.dto.ProductCompositionItemInput;

public record UpdateProductInput(
  UUID id,
  String name,
  BigDecimal value,
  List<ProductCompositionItemInput> composition
) {}
