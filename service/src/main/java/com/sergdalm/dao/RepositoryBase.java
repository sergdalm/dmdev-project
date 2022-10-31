package com.sergdalm.dao;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@Slf4j
public abstract class RepositoryBase<E, T extends Serializable> implements Repository<E, T> {

    private final Class<E> clazz;

    @Getter(AccessLevel.PROTECTED)
    private final EntityManager entityManager;

    public RepositoryBase(Class<E> clazz, EntityManager entityManager) {
        this.clazz = clazz;
        this.entityManager = entityManager;
    }

    @Override
    public E save(E entity) {
        entityManager.persist(entity);
        return entity;
    }

    @Override
    public Optional<E> findById(T id) {
        return Optional.ofNullable(entityManager.find(clazz, id));
    }

    @Override
    public List<E> findAll() {
        CriteriaQuery<E> criteria = entityManager.getCriteriaBuilder()
                .createQuery(clazz);
        criteria.from(clazz);
        return entityManager.createQuery(criteria)
                .getResultList();
    }

    @Override
    public void delete(E entity) {
        log.info("START DELETING");
        entityManager.remove(entity);
        log.info("DELETED");
        entityManager.flush();
        log.info("FLUSHED");
    }

    @Override
    public void update(E entity) {
        entityManager.merge(entity);
    }
}
