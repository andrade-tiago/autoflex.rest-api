package dev.andrade.tiago.domain.models;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Product {
  private final UUID id;
  private String name;
  private BigDecimal value;
  private Map<UUID, ProductCompositionItem> composition = new HashMap<>();

  public Product(UUID id, String name, BigDecimal value) {
    this.id = id;
    this.setName(name);
    this.setValue(value);
  }
  public static Product create(String name, BigDecimal value) {
    return new Product(UUID.randomUUID(), name, value);
  }

  public UUID getId() { return this.id; }

  public void setName(String name) {
    if (name == null)
      throw new IllegalArgumentException("Product name is required");
    if (name.isBlank())
      throw new IllegalArgumentException("Product name cannot be blank");

    this.name = name;
  }
  public String getName() { return this.name; }

  public void setValue(BigDecimal value) {
    if (value == null)
      throw new IllegalArgumentException("Product value is required");
    if (value.compareTo(BigDecimal.ZERO) < 0)
      throw new IllegalArgumentException("Product value cannot be negative");

    this.value = value;
  }
  public BigDecimal getValue() { return this.value; }

  public Map<UUID, ProductCompositionItem> getComposition() {
    return Map.copyOf(this.composition);
  }
  public void setCompositionItem(ProductCompositionItem item) {
    this.composition.put(item.getRawMaterialId(), item);
  }
  public void setCompositionItem(UUID materialId, int requiredQuantity) {
    this.composition.put(materialId,
      new ProductCompositionItem(materialId, requiredQuantity)
    );
  }
  public ProductCompositionItem removeCompositionItem(UUID rawMaterialId) {
    return this.composition.remove(rawMaterialId);
  }
}
