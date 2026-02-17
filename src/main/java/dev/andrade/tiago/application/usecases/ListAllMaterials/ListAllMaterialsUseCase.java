package dev.andrade.tiago.application.usecases.ListAllMaterials;

import java.util.List;

import dev.andrade.tiago.application.dto.RawMaterialOutput;
import dev.andrade.tiago.domain.repositories.RawMaterialRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ListAllMaterialsUseCase {
  private RawMaterialRepository repo;

  public ListAllMaterialsUseCase(RawMaterialRepository repo) {
    this.repo = repo;
  }

  public ListAllMaterialsOutput execute() {
    var materials = this.repo.getAll();

    List<RawMaterialOutput> data = materials.stream()
      .map(item -> new RawMaterialOutput(
        item.getId(),
        item.getName(),
        item.getStock()
      ))
      .toList();

    return new ListAllMaterialsOutput(data);
  }
}
