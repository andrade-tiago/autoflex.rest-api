package dev.andrade.tiago.application.usecases.ListAllProducts;

import java.util.List;

import dev.andrade.tiago.application.dto.ProductCompositionItemOutput;
import dev.andrade.tiago.application.dto.ProductOutput;
import dev.andrade.tiago.domain.models.Product;
import dev.andrade.tiago.domain.models.ProductCompositionItem;
import dev.andrade.tiago.domain.repositories.ProductRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class ListAllProductsUseCase {
  private ProductRepository repo;

  public ListAllProductsUseCase(
    ProductRepository repo
  ) {
    this.repo = repo;
  }

  @Transactional
  public ListAllProductsOutput execute() {
    List<Product> products = this.repo.getAllOrderedByNameAsc();

    var productOuputs = products.stream()
      .map(this::mapProductToOutput)
      .toList();

    return new ListAllProductsOutput(productOuputs);
  }

  public ProductOutput mapProductToOutput(Product product) {
    return new ProductOutput(
      product.getId(),
      product.getName(),
      product.getValue(),
      product.getComposition()
        .values()
        .stream()
        .map(this::mapCompositionItemToOutput)
        .toList()
    );
  }

  public ProductCompositionItemOutput mapCompositionItemToOutput(
    ProductCompositionItem item
  ) {
    return new ProductCompositionItemOutput(
      item.getRawMaterial().getId(),
      item.getRequiredQuantity()
    );
  }
}
