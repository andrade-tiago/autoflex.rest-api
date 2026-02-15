package dev.andrade.tiago.application.usecases.CreateProduct;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import dev.andrade.tiago.application.exceptions.ResourceNotFoundException;
import dev.andrade.tiago.domain.models.Product;
import dev.andrade.tiago.domain.models.RawMaterial;
import dev.andrade.tiago.domain.repositories.ProductRepository;
import dev.andrade.tiago.domain.repositories.RawMaterialRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class CreateProductUseCase {
  private ProductRepository productRepo;
  private RawMaterialRepository materialRepo;

  public CreateProductUseCase(
    ProductRepository productRepo,
    RawMaterialRepository materialRepo
  ) {
    this.productRepo = productRepo;
    this.materialRepo = materialRepo;
  }

  @Transactional
  public UUID execute(CreateProductInput input) {
    var product = Product.create(
      input.name(),
      input.value()
    );

    List<UUID> materialIDs = input.composition()
      .stream()
      .map(item -> item.rawMaterialID())
      .toList();
    
    List<RawMaterial> materials = this.materialRepo.getByIds(materialIDs);

    Map<UUID, RawMaterial> materialsByID = materials.stream()
      .collect(Collectors.toMap(
        material -> material.getId(),
        material -> material
      ));

    List<UUID> materialsNotFoundUUIDs = new ArrayList<>();
    for (var item : input.composition()) {
      var material = materialsByID.get(item.rawMaterialID());

      if (material == null) {
        materialsNotFoundUUIDs.add(item.rawMaterialID());
        continue;
      }
      product.setCompositionItem(
        material,
        item.requiredQuantity()
      );
    }
    if (!materialsNotFoundUUIDs.isEmpty()) {
      throw new ResourceNotFoundException(
        "One or more raw materials not found",
        materialsNotFoundUUIDs
      );
    }

    this.productRepo.save(product);
    return product.getId();
  }
}
