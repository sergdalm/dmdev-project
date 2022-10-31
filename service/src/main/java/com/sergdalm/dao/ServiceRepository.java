package com.sergdalm.dao;

import com.sergdalm.entity.Service;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class ServiceRepository extends RepositoryBase<Service, Integer> {

    public ServiceRepository(EntityManager entityManager) {
        super(Service.class, entityManager);
    }
}
