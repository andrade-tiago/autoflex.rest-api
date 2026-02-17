package dev.andrade.tiago.application.usecases.DeleteProduct;

import java.util.UUID;

import dev.andrade.tiago.domain.repositories.ProductRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class DeleteProductUseCase {
  private ProductRepository repo;

  public DeleteProductUseCase(
    ProductRepository repo
  ) {
    this.repo = repo;
  }

  @Transactional
  public void execute(UUID productId) {
    this.repo.deleteById(productId);
  }
}
