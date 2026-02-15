package dev.andrade.tiago.domain.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import dev.andrade.tiago.domain.models.RawMaterial;

public interface RawMaterialRepository {
  void save(RawMaterial rawMaterial);
  Optional<RawMaterial> getById(UUID id);
  List<RawMaterial> getByIds(List<UUID> ids);
  boolean deleteById(UUID id);
}
