package com.sergdalm.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface Repository<E, T extends Serializable> {

    E save(E entity);

    void delete(E entity);

    void update(E entity);

    Optional<E> findById(T id);

    List<E> findAll();
}
