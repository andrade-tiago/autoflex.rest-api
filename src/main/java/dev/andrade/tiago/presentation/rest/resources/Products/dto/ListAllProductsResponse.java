package dev.andrade.tiago.presentation.rest.resources.Products.dto;

import java.util.List;

import dev.andrade.tiago.application.dto.ProductOutput;

public record ListAllProductsResponse(
  List<ProductOutput> data
) {}
