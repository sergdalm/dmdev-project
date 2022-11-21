package com.sergdalm.service;

import java.util.List;
import java.util.Optional;

public interface GenericService<T, C, R> {

    List<R> findAll();

    Optional<R> findById(T id);

    R create(C dto);

    Optional<R> update(T id, C dto);

    boolean delete(T id);
}
