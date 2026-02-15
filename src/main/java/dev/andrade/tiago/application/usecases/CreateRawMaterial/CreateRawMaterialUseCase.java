package dev.andrade.tiago.application.usecases.CreateRawMaterial;

import java.util.UUID;

import dev.andrade.tiago.domain.models.RawMaterial;
import dev.andrade.tiago.domain.repositories.RawMaterialRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class CreateRawMaterialUseCase {
  private final RawMaterialRepository repo;

  public CreateRawMaterialUseCase(RawMaterialRepository repo) {
    this.repo = repo;
  }

  @Transactional
  public UUID execute(CreateRawMaterialInput input) {
    var rawMaterial = RawMaterial.create(
      input.name(),
      input.stock()
    );

    this.repo.save(rawMaterial);

    return rawMaterial.getId();
  }
}
