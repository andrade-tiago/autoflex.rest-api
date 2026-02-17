package dev.andrade.tiago.application.usecases.UpdateRawMaterial;

import dev.andrade.tiago.application.dto.RawMaterialOutput;
import dev.andrade.tiago.domain.models.RawMaterial;
import dev.andrade.tiago.domain.repositories.RawMaterialRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class UpdateRawMaterialUseCase {
  private RawMaterialRepository repo;

  public UpdateRawMaterialUseCase(
    RawMaterialRepository repo
  ) {
    this.repo = repo;
  }

  @Transactional
  public RawMaterialOutput execute(UpdateRawMaterialInput input) {
    RawMaterial updatedMaterial = this.repo.update(
      input.id(),
      input.name(),
      input.stock()
    );
    return new RawMaterialOutput(
      updatedMaterial.getId(),
      updatedMaterial.getName(),
      updatedMaterial.getStock()
    );
  }
}
