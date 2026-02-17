package dev.andrade.tiago.presentation.rest.dto;

import java.util.UUID;

public record UpdateRawMaterialResponse(
  UUID id,
  String name,
  int stock
) {}
