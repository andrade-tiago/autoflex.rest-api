package dev.andrade.tiago.presentation.rest.resources.RawMaterials.dto;

public record UpdateRawMaterialRequest(
  String name,
  Integer stock
) {}
