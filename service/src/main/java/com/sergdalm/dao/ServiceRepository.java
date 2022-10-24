package com.sergdalm.dao;

import com.sergdalm.entity.Service;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

@Repository
public class ServiceRepository extends RepositoryBase<Service, Integer> {

    public ServiceRepository(SessionFactory sessionFactory) {
        super(Service.class, sessionFactory);
    }
}
