package dev.andrade.tiago.infra.persistence.types;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

import jakarta.persistence.Embeddable;

@Embeddable
public class ProductCompositionItemEntityId implements Serializable {
  public UUID productId;
  public UUID rawMaterialId;

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;

    if (!(obj instanceof ProductCompositionItemEntityId that))
      return false;

    return
      Objects.equals(productId, that.productId)
      && Objects.equals(rawMaterialId, that.rawMaterialId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(productId, rawMaterialId);
  }
}
