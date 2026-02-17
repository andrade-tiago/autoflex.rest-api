package dev.andrade.tiago.presentation.rest.RawMaterials.dto;

public record UpdateRawMaterialRequest(
  String name,
  Integer stock
) {}
