package dev.andrade.tiago.domain.models;

public class ProductCompositionItem {
  private final RawMaterial rawMaterial;
  private int requiredQuantity;

  public ProductCompositionItem(RawMaterial rawMaterial, int requiredQuantity) {
    this.rawMaterial = rawMaterial;
    this.setRequiredQuantity(requiredQuantity);
  }

  public RawMaterial getRawMaterial() { return this.rawMaterial; }

  public void setRequiredQuantity(int quantity) {
    if (quantity <= 0)
      throw new IllegalArgumentException("Quantity required must be positive");

    this.requiredQuantity = quantity;
  }
  public int getRequiredQuantity() { return this.requiredQuantity; }
}
