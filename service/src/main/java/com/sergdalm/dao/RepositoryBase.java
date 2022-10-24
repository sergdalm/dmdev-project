package com.sergdalm.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.criteria.CriteriaQuery;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public abstract class RepositoryBase<E, T extends Serializable> implements Repository<E, T> {

    private final Class<E> clazz;
    private final SessionFactory sessionFactory;

    public RepositoryBase(Class<E> clazz, SessionFactory sessionFactory) {
        this.clazz = clazz;
        this.sessionFactory = sessionFactory;
    }

    @Override
    public E save(E entity) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(entity);
        return entity;
    }

    @Override
    public Optional<E> findById(T id) {
        return Optional.ofNullable(sessionFactory.getCurrentSession().find(clazz, id));
    }

    @Override
    public List<E> findAll() {
        Session session = sessionFactory.getCurrentSession();
        CriteriaQuery<E> criteria = session.getCriteriaBuilder()
                .createQuery(clazz);
        criteria.from(clazz);
        return session.createQuery(criteria)
                .getResultList();
    }

    @Override
    public void delete(E entity) {
        Session session = sessionFactory.getCurrentSession();
        session.remove(entity);
        session.flush();
    }

    @Override
    public void update(E entity) {
        Session session = sessionFactory.getCurrentSession();
        session.merge(entity);
    }

    protected SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
