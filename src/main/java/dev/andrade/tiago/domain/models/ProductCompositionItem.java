package dev.andrade.tiago.domain.models;

import java.util.UUID;

public class ProductCompositionItem {
  private final UUID rawMaterialId;
  private int requiredQuantity;

  public ProductCompositionItem(UUID rawMaterialId, int requiredQuantity) {
    this.rawMaterialId = rawMaterialId;
    this.setRequiredQuantity(requiredQuantity);
  }

  public UUID getRawMaterialId() { return this.rawMaterialId; }

  public void setRequiredQuantity(int quantity) {
    if (quantity <= 0)
      throw new IllegalArgumentException("Quantity required must be positive");

    this.requiredQuantity = quantity;
  }
  public int getRequiredQuantity() { return this.requiredQuantity; }
}
