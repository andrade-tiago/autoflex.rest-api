package dev.andrade.tiago.domain.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import dev.andrade.tiago.domain.models.Product;

public interface ProductRepository {
  void save(Product product);
  Optional<Product> getById(UUID id);
  boolean deleteById(UUID id);
  List<Product> getAllWithCompositionOrderedByValueDesc();
}
