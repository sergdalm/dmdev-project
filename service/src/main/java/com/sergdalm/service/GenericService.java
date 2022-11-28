package com.sergdalm.service;

import java.util.List;
import java.util.Optional;

public interface GenericService<ID, C, R> {

    List<R> findAll();

    Optional<R> findById(ID id);

    R create(C dto);

    Optional<R> update(ID id, C dto);

    boolean delete(ID id);
}
