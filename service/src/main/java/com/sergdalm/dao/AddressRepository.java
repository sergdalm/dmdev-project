package com.sergdalm.dao;

import com.sergdalm.entity.Address;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

@Repository
public class AddressRepository extends RepositoryBase<Address, Integer> {

    public AddressRepository(SessionFactory sessionFactory) {
        super(Address.class, sessionFactory);
    }
}
