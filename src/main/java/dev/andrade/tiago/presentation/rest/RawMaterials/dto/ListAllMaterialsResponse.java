package dev.andrade.tiago.presentation.rest.RawMaterials.dto;

import java.util.List;

import dev.andrade.tiago.application.dto.RawMaterialOutput;

public record ListAllMaterialsResponse(
  List<RawMaterialOutput> data
) {}
