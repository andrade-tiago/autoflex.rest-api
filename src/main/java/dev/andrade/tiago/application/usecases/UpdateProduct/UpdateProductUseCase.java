package dev.andrade.tiago.application.usecases.UpdateProduct;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

import dev.andrade.tiago.application.dto.ProductCompositionItemInput;
import dev.andrade.tiago.application.dto.ProductCompositionItemOutput;
import dev.andrade.tiago.application.dto.ProductOutput;
import dev.andrade.tiago.application.exceptions.ResourceNotFoundException;
import dev.andrade.tiago.domain.models.Product;
import dev.andrade.tiago.domain.models.ProductCompositionItem;
import dev.andrade.tiago.domain.models.RawMaterial;
import dev.andrade.tiago.domain.repositories.ProductRepository;
import dev.andrade.tiago.domain.repositories.RawMaterialRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class UpdateProductUseCase {
  private RawMaterialRepository materialsRepo;
  private ProductRepository productsRepo;

  public UpdateProductUseCase(
    RawMaterialRepository materialRepo,
    ProductRepository productsRepo
  ) {
    this.materialsRepo = materialRepo;
    this.productsRepo = productsRepo;
  }

  @Transactional
  public ProductOutput execute(UpdateProductInput input) {
    Product product = this.productsRepo.getById(input.id())
      .orElseThrow(() ->
        new ResourceNotFoundException(
          "Material with ID not found",
          input.id()));
    
    if (input.name() != null)
      product.setName(input.name());
    if (input.value() != null)
      product.setValue(input.value());

    updateProductComposition(product, input.composition());

    this.productsRepo.update(product);

    var compositionItemsOutput = product.getComposition()
      .values()
      .stream()
      .map(item ->
        new ProductCompositionItemOutput(
          item.getRawMaterialId(),
          item.getRequiredQuantity()
        )
      )
      .toList();

    return new ProductOutput(
      product.getId(),
      product.getName(),
      product.getValue(),
      compositionItemsOutput
    );
  }

  private void updateProductComposition(
    Product product,
    List<ProductCompositionItemInput> items
  ) {
    List<UUID> materialIDs = items.stream()
      .map(ProductCompositionItemInput::rawMaterialId)
      .toList();

    Map<UUID, RawMaterial> materialsById = this.materialsRepo
      .getByIds(materialIDs)
      .stream()
      .collect(
        Collectors.toMap(RawMaterial::getId, Function.identity())
      );

    List<ProductCompositionItem> newComposition = new ArrayList<>();
    List<UUID> materialsNotFoundUUIDs = new ArrayList<>();

    for (var item : items) {
      var material = materialsById.get(item.rawMaterialId());

      if (material == null) {
        materialsNotFoundUUIDs.add(item.rawMaterialId());
        continue;
      }
      newComposition.add(
        new ProductCompositionItem(
          item.rawMaterialId(),
          item.requiredQuantity()));
    }

    if (!materialsNotFoundUUIDs.isEmpty()) {
      throw new ResourceNotFoundException(
        "One or more raw materials not found",
        materialsNotFoundUUIDs
      );
    }
    product.replaceComposition(newComposition);
  }
}
