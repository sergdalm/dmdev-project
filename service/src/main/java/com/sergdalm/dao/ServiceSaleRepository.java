package com.sergdalm.dao;

import com.sergdalm.entity.ServiceSale;
import org.hibernate.SessionFactory;

public class ServiceSaleRepository extends RepositoryBase<ServiceSale, Integer> {

    private final SessionFactory sessionFactory;

    public ServiceSaleRepository(SessionFactory sessionFactory) {
        super(ServiceSale.class, sessionFactory);
        this.sessionFactory = sessionFactory;
    }
}
