package dev.andrade.tiago.domain.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import dev.andrade.tiago.domain.models.RawMaterial;

public interface RawMaterialRepository {
  void add(RawMaterial rawMaterial);
  void update(RawMaterial rawMaterial);
  Optional<RawMaterial> getById(UUID id);
  List<RawMaterial> getByIds(List<UUID> ids);
  List<RawMaterial> getAll();
  boolean deleteById(UUID id);
}
