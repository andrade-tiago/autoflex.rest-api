package dev.andrade.tiago.application.usecases.SuggestProduction;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

import dev.andrade.tiago.domain.models.Product;
import dev.andrade.tiago.domain.models.RawMaterial;
import dev.andrade.tiago.domain.repositories.ProductRepository;
import dev.andrade.tiago.domain.repositories.RawMaterialRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class SuggestProductionUseCase {
  private ProductRepository productsRepo;
  private RawMaterialRepository materialsRepo;

  public SuggestProductionUseCase(
    ProductRepository productsRepo,
    RawMaterialRepository materialsRepo
  ) {
    this.productsRepo = productsRepo;
    this.materialsRepo = materialsRepo;
  }

  @Transactional
  public SuggestProductionOutput execute() {
    List<Product> products = this.productsRepo.getAllOrderedByValueDesc();
    Map<UUID, RawMaterial> materials = getNeededMaterialsMappedById(products);

    List<SuggestProductionOutputItem> suggestions = new ArrayList<>();
    for (var product : products) {
      int quantity = maxProductQuantityThatCanBeProduced(product, materials);

      if (quantity == 0)
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

    return new SuggestProductionOutput(totalValue, suggestions);
  }

  private Map<UUID, RawMaterial> getNeededMaterialsMappedById(
    List<Product> products
  ) {
    Set<UUID> materialsNeeded = new HashSet<>();
    for (var product : products) {
      materialsNeeded.addAll(
        product.getComposition().keySet()
      );
    }

    List<RawMaterial> materials = this.materialsRepo.getByIds(
      materialsNeeded.stream().toList()
    );
    return materials.stream().collect(
      Collectors.toMap(
        RawMaterial::getId,
        Function.identity()
      )
    );
  }

  private int maxProductQuantityThatCanBeProduced(
    Product product,
    Map<UUID, RawMaterial> materials
  ) {
    int maxQuantity = Integer.MAX_VALUE;

    for (var item : product.getComposition().values()) {
      RawMaterial material = materials.get(
        item.getRawMaterialId()
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
        item.getRawMaterialId()
      );

      material.subtractFromStock(
        productQuantity * item.getRequiredQuantity()
      );
    }
  }
}
