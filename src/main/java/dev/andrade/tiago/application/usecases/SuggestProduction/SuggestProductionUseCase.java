package dev.andrade.tiago.application.usecases.SuggestProduction;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import dev.andrade.tiago.domain.models.Product;
import dev.andrade.tiago.domain.models.RawMaterial;
import dev.andrade.tiago.domain.repositories.ProductRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class SuggestProductionUseCase {
  private ProductRepository productsRepo;

  public SuggestProductionUseCase(
    ProductRepository productsRepo
  ) {
    this.productsRepo = productsRepo;
  }

  @Transactional
  public SuggestProductionOutput execute() {
    List<SuggestProductionOutputItem> suggestions = new ArrayList<>();

    List<Product> products = this.productsRepo.getAllWithCompositionOrderedByValueDesc();
    Map<UUID, RawMaterial> materials = getAllMaterialsMappedById(products);

    for (var product : products) {
      int quantity = maxProductQuantityThatCanBeProduced(product, materials);

      if (quantity <= 0)
        continue;

      updateMaterialLocalInventory(product, quantity, materials);

      BigDecimal totalValue = product.getValue()
        .multiply(new BigDecimal(quantity));

      suggestions.add(new SuggestProductionOutputItem(
        product.getId(),
        product.getName(),
        quantity,
        product.getValue(),
        totalValue
      ));
    }
    BigDecimal totalValue = suggestions.stream()
      .map(SuggestProductionOutputItem::totalValue)
      .reduce(BigDecimal.ZERO, BigDecimal::add);

    return new SuggestProductionOutput(
      totalValue,
      suggestions
    );
  }

  private Map<UUID, RawMaterial> getAllMaterialsMappedById(
    List<Product> products
  ) {
    Map<UUID, RawMaterial> materials = new HashMap<>();

    for (var product : products)
    {
      for (var item : product.getComposition().values())
      {
        var material = item.getRawMaterial();

        materials.putIfAbsent(material.getId(), material);
      }
    }
    return materials;
  }

  private int maxProductQuantityThatCanBeProduced(
    Product product,
    Map<UUID, RawMaterial> materials
  ) {
    int maxQuantity = Integer.MAX_VALUE;

    for (var item : product.getComposition().values()) {
      RawMaterial material = materials.get(
        item.getRawMaterial().getId()
      );

      int quantity =
        material.getStock() / item.getRequiredQuantity();

      maxQuantity = Math.min(quantity, maxQuantity);
    }
    return maxQuantity;
  }

  private void updateMaterialLocalInventory(
    Product product,
    int productQuantity,
    Map<UUID, RawMaterial> materialsInStock
  ) {
    for (var item : product.getComposition().values()) {
      RawMaterial material = materialsInStock.get(
        item.getRawMaterial().getId()
      );

      material.subtractFromStock(
        productQuantity * item.getRequiredQuantity()
      );
    }
  }
}
