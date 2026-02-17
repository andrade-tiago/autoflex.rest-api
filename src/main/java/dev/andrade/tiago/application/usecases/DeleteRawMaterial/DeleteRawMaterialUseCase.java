package dev.andrade.tiago.application.usecases.DeleteRawMaterial;

import java.util.UUID;

import dev.andrade.tiago.domain.repositories.RawMaterialRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class DeleteRawMaterialUseCase {
  private RawMaterialRepository repo;

  public DeleteRawMaterialUseCase(
    RawMaterialRepository repo
  ) {
    this.repo = repo;
  }

  @Transactional
  public void execute(UUID materialId) {
    this.repo.deleteById(materialId);
  }
}
