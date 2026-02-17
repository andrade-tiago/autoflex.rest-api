package dev.andrade.tiago.presentation.rest.dto;

import java.util.List;

import dev.andrade.tiago.application.dto.ProductOutput;

public record ListAllProductsResponse(
  List<ProductOutput> data
) {}
