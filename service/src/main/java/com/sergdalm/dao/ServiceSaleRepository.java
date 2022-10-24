package com.sergdalm.dao;

import com.sergdalm.entity.ServiceSale;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

@Repository
public class ServiceSaleRepository extends RepositoryBase<ServiceSale, Integer> {

    public ServiceSaleRepository(SessionFactory sessionFactory) {
        super(ServiceSale.class, sessionFactory);
    }
}
