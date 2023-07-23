package com.javarush.repository;

import java.util.List;
import java.util.Optional;

public interface BaseRepository<K, E> {

  Optional<E> findById(K id);

  void deleteById(K id);

  E update(E entity);

  E save(E entity);

  void delete(E entity);

  List<E> findAll();
}
