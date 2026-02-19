package dev.andrade.tiago.presentation.rest.resources.RawMaterials.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;

public record CreateRawMaterialRequest(
  @NotBlank
  String name,

  @PositiveOrZero
  int stock
) {}
