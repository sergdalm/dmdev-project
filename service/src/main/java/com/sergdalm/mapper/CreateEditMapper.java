package com.sergdalm.mapper;

public interface CreateEditMapper<E, D> extends Mapper<E, D> {

    E mapToEntity(D dto);

    E mapToEntity(D dto, E entity);
}
