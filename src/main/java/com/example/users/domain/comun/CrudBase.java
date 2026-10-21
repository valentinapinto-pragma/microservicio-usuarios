package com.example.users.domain.comun;

import java.util.List;
import java.util.Optional;

public interface CrudBase<T, ID> {
    Optional<T> findById(ID id);
    List<T> findAll();
    T save(T entity);
    void update(T entity);
    void delete(T entity);
}
