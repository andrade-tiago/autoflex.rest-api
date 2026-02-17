package dev.andrade.tiago.infra.persistence.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import dev.andrade.tiago.domain.models.Product;
import dev.andrade.tiago.domain.repositories.ProductRepository;
import dev.andrade.tiago.infra.persistence.entities.ProductEntity;
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
}
