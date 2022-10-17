package com.sergdalm.dao;

import com.sergdalm.entity.Address;
import org.hibernate.SessionFactory;

public class AddressRepository extends RepositoryBase<Address, Integer> {

    private final SessionFactory sessionFactory;

    public AddressRepository(SessionFactory sessionFactory) {
        super(Address.class, sessionFactory);
        this.sessionFactory = sessionFactory;
    }
}
