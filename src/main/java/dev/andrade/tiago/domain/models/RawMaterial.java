package dev.andrade.tiago.domain.models;

import java.util.UUID;

public class RawMaterial {
  private final UUID id;
  private String name;
  private int stock;

  public RawMaterial(UUID id, String name, int stock) {
    this.id = id;
    this.setName(name);
    this.setStock(stock);
  }
  public static RawMaterial create(String name, int stock) {
    return new RawMaterial(UUID.randomUUID(), name, stock);
  }

  public UUID getId() { return this.id; }

  public void setName(String name) {
    if (name == null)
      throw new IllegalArgumentException("Material name is required.");
    if (name.isBlank())
      throw new IllegalArgumentException("Material name cannot be blank");

    this.name = name;
  }
  public String getName() { return this.name; }

  public void setStock(int stock) {
    if (stock < 0)
      throw new IllegalArgumentException("Material stock cannot be negative");

    this.stock = stock;
  }
  public int getStock() { return this.stock; }
}
