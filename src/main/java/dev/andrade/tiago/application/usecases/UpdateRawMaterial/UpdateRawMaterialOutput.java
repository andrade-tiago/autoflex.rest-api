package dev.andrade.tiago.application.usecases.UpdateRawMaterial;

import java.util.UUID;

public record UpdateRawMaterialOutput(
  UUID id,
  String name,
  int stock
) {}
