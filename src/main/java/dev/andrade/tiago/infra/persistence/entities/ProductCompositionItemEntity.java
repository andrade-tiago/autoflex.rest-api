package dev.andrade.tiago.infra.persistence.entities;

import dev.andrade.tiago.domain.models.ProductCompositionItem;
import dev.andrade.tiago.infra.persistence.types.ProductCompositionItemEntityId;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

@Entity
@Table(name = "product_composition_items")
public class ProductCompositionItemEntity extends PanacheEntityBase {
  @EmbeddedId
  public ProductCompositionItemEntityId id;

  @ManyToOne
  @MapsId("productId")
  @JoinColumn(name = "product_id")
  public ProductEntity product;

  @Column(name = "required_quantity")
  public int requiredQuantity;

  public ProductCompositionItem toDomain() {
    return new ProductCompositionItem(
      this.id.rawMaterialId,
      this.requiredQuantity
    );
  }
}
