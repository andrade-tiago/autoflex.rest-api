package dev.andrade.tiago.application.dto;

import java.util.UUID;

public record RawMaterialOutput(
  UUID id,
  String name,
  int stock
) {}
