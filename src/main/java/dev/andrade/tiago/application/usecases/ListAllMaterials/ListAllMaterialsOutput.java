package dev.andrade.tiago.application.usecases.ListAllMaterials;

import java.util.List;

import dev.andrade.tiago.application.dto.RawMaterialOutput;

public record ListAllMaterialsOutput(
  List<RawMaterialOutput> data
) {}
