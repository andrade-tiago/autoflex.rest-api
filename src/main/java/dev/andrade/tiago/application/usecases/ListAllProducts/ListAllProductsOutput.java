package dev.andrade.tiago.application.usecases.ListAllProducts;

import java.util.List;

import dev.andrade.tiago.application.dto.ProductOutput;

public record ListAllProductsOutput(
  List<ProductOutput> data
) {}
