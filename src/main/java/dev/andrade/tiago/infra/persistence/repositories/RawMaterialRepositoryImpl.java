package dev.andrade.tiago.infra.persistence.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import dev.andrade.tiago.application.exceptions.ResourceNotFoundException;
import dev.andrade.tiago.domain.models.RawMaterial;
import dev.andrade.tiago.domain.repositories.RawMaterialRepository;
import dev.andrade.tiago.infra.persistence.entities.RawMaterialEntity;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class RawMaterialRepositoryImpl implements RawMaterialRepository {
  @Override
  public void add(RawMaterial rawMaterial) {
    RawMaterialEntity entity = RawMaterialEntity.fromDomain(rawMaterial);
    entity.persist();
  }

  @Override
  public Optional<RawMaterial> getById(UUID id) {
    Optional<RawMaterialEntity> entity = RawMaterialEntity.findByIdOptional(id);
    return entity.map(RawMaterialEntity::toDomain);
  }

  @Override
  public List<RawMaterial> getByIds(List<UUID> ids) {
    if (ids == null || ids.isEmpty())
      return List.of();

    List<RawMaterialEntity> entities = RawMaterialEntity.findByIds(ids);
    return entities.stream()
      .filter(item -> item != null)
      .map(RawMaterialEntity::toDomain)
      .toList();
  }

  @Override
  public List<RawMaterial> getAll() {
    List<RawMaterialEntity> entities = RawMaterialEntity.listAll();
    return entities.stream()
      .map(RawMaterialEntity::toDomain)
      .toList();
  }

  @Override
  public boolean deleteById(UUID id) {
    return RawMaterialEntity.deleteById(id);
  }

  @Override
  public void update(RawMaterial material) {
    RawMaterialEntity entity = RawMaterialEntity.findById(material.getId());
    if (entity == null)
      throw new ResourceNotFoundException(
        "Material with ID not found",
        material.getId()
      );

    entity.name = material.getName();
    entity.stock = material.getStock();
  }
}
