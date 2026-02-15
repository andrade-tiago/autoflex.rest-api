package dev.andrade.tiago.presentation.rest.dto;

import java.util.UUID;

public record RawMaterialResponse(
  UUID id,
  String name,
  int stock
) {}
