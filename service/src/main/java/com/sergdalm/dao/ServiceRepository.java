package com.sergdalm.dao;

import com.sergdalm.entity.Service;
import org.hibernate.SessionFactory;

public class ServiceRepository extends RepositoryBase<Service, Integer> {

    private final SessionFactory sessionFactory;

    public ServiceRepository(SessionFactory sessionFactory) {
        super(Service.class, sessionFactory);
        this.sessionFactory = sessionFactory;
    }
}
