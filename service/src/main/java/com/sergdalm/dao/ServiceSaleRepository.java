package com.sergdalm.dao;

import com.sergdalm.entity.ServiceSale;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class ServiceSaleRepository extends RepositoryBase<ServiceSale, Integer> {

    public ServiceSaleRepository(EntityManager entityManager) {
        super(ServiceSale.class, entityManager);
    }
}
