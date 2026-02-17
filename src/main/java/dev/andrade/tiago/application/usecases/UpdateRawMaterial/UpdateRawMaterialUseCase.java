package dev.andrade.tiago.application.usecases.UpdateRawMaterial;

import dev.andrade.tiago.application.dto.RawMaterialOutput;
import dev.andrade.tiago.application.exceptions.ResourceNotFoundException;
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
    RawMaterial material = this.repo.getById(input.id())
      .orElseThrow(() ->
        new ResourceNotFoundException(
          "Material with ID not found",
          input.id()));

    if (input.name() != null)
      material.setName(input.name());
    if (input.stock() != null)
      material.setStock(input.stock());

    this.repo.update(material);
    return new RawMaterialOutput(
      material.getId(),
      material.getName(),
      material.getStock()
    );
  }
}
