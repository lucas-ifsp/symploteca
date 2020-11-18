package br.edu.ifsp.domain.usecases.utils;

import java.util.List;
import java.util.Optional;

public interface DAO <T, K>{
    K create(T type);

    Optional<T> findOne(K key);

    List<T> findAll();

    boolean update(T type);

    boolean deleteByKey(K key);

    boolean delete(T type);
}
