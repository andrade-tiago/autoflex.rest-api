package dev.andrade.tiago.infra.persistence.entities;

import java.util.UUID;

import dev.andrade.tiago.domain.models.RawMaterial;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "raw_materials")
public class RawMaterialEntity extends PanacheEntityBase {
  @Id
  @Column(columnDefinition = "uuid")
  public UUID id;

  public String name;
  public int stock;

  public static RawMaterialEntity fromDomain(RawMaterial domain) {
    RawMaterialEntity entity = new RawMaterialEntity();
    entity.id = domain.getId();
    entity.name = domain.getName();
    entity.stock = domain.getStock();
    return entity;
  }

  public RawMaterial toDomain() {
    return new RawMaterial(id, name, stock);
  }
}
