package dev.andrade.tiago.infra.persistence.entities;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import dev.andrade.tiago.domain.models.Product;
import dev.andrade.tiago.domain.models.ProductCompositionItem;
import dev.andrade.tiago.infra.persistence.types.ProductCompositionItemEntityId;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "products")
public class ProductEntity extends PanacheEntityBase {
  @Id
  @Column(columnDefinition = "uuid")
  UUID id;

  public String name;
  public BigDecimal value;

  @OneToMany(
    mappedBy = "product",
    cascade = CascadeType.ALL,
    orphanRemoval = true
  )
  public List<ProductCompositionItemEntity> composition = new ArrayList<>();

  public Product toDomain() {
    var product = new Product(id, name, value);

    for (ProductCompositionItemEntity item : this.composition) {
      product.setCompositionItem(
        item.rawMaterial.toDomain(),
        item.requiredQuantity
      );
    }
    return product;
  }

  public static ProductEntity fromDomain(Product domain) {
    var entity = new ProductEntity();
    entity.id = domain.getId();
    entity.name = domain.getName();
    entity.value = domain.getValue();

    var items = domain.getComposition().values();
    for (ProductCompositionItem item : items) {
      var itemEntity = new ProductCompositionItemEntity();

      var itemEntityID = new ProductCompositionItemEntityId();
      itemEntityID.productId = domain.getId();
      itemEntityID.rawMaterialId = item.getRawMaterial().getId();

      itemEntity.id = itemEntityID;
      itemEntity.product = entity;
      itemEntity.rawMaterial = RawMaterialEntity.fromDomain(item.getRawMaterial());
      itemEntity.requiredQuantity = item.getRequiredQuantity();

      entity.composition.add(itemEntity);
    }
    return entity;
  }
}
