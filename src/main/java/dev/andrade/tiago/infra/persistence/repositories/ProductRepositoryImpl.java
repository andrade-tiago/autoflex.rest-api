package dev.andrade.tiago.infra.persistence.repositories;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import dev.andrade.tiago.application.exceptions.ResourceNotFoundException;
import dev.andrade.tiago.domain.models.Product;
import dev.andrade.tiago.domain.models.ProductCompositionItem;
import dev.andrade.tiago.domain.repositories.ProductRepository;
import dev.andrade.tiago.infra.persistence.entities.ProductCompositionItemEntity;
import dev.andrade.tiago.infra.persistence.entities.ProductEntity;
import dev.andrade.tiago.infra.persistence.types.ProductCompositionItemEntityId;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ProductRepositoryImpl implements ProductRepository {
  @Override
  public void save(Product product) {
    var entity = ProductEntity.fromDomain(product);
    entity.persist();
  }

  @Override
  public boolean deleteById(UUID id) {
    return ProductEntity.deleteById(id);
  }

  @Override
  public Optional<Product> getById(UUID id) {
    Optional<ProductEntity> entity = ProductEntity.findByIdOptional(id);
    return entity.map(ProductEntity::toDomain);
  }

  @Override
  public List<Product> getAllOrderedByNameAsc() {
    List<ProductEntity> entities = ProductEntity.find("""
      select distinct p from ProductEntity p
      left join fetch p.composition c
      order by p.name asc
    """).list();

    return entities.stream()
      .map(ProductEntity::toDomain)
      .toList();
  }

  @Override
  public List<Product> getAllOrderedByValueDesc() {
    List<ProductEntity> entities = ProductEntity.find("""
      select distinct p from ProductEntity p
      left join fetch p.composition c
      order by p.value desc
    """).list();

    return entities.stream()
      .map(ProductEntity::toDomain)
      .toList();
  }

  @Override
  public void update(Product product) {
    ProductEntity entity = ProductEntity.findById(product.getId());
    if (entity == null)
      throw new ResourceNotFoundException(
        "Product with ID not found",
        product.getId());

    entity.name = product.getName();
    entity.value = product.getValue();
    updateEntityComposition(product, entity);
  }

  private void updateEntityComposition(Product domain, ProductEntity entity) {
    Map<UUID, ProductCompositionItem> incoming = domain.getComposition();

    entity.composition.removeIf(item ->
      !incoming.containsKey(item.id.rawMaterialId)
    );

    Map<UUID, ProductCompositionItemEntity> existing = entity.composition
      .stream()
      .collect(Collectors.toMap(
        e -> e.id.rawMaterialId,
        e -> e
      ));

    for (var entry : incoming.entrySet()) {
      UUID rawMaterialId = entry.getKey();
      ProductCompositionItem domainItem = entry.getValue();

      if (existing.containsKey(rawMaterialId)) {
        var entityItem = existing.get(rawMaterialId);

        entityItem.requiredQuantity = domainItem.getRequiredQuantity();
        continue;
      }
      var id = new ProductCompositionItemEntityId();
      id.productId = domain.getId();
      id.rawMaterialId = rawMaterialId;

      var newEntity = new ProductCompositionItemEntity();
      newEntity.id = id;
      newEntity.product = entity;
      newEntity.requiredQuantity = domainItem.getRequiredQuantity();

      entity.composition.add(newEntity);
    }
  }
}
