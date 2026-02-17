package dev.andrade.tiago.application.usecases.UpdateRawMaterial;

import java.util.UUID;

public record UpdateRawMaterialInput(
  UUID id,
  String name,
  Integer stock
) {}
