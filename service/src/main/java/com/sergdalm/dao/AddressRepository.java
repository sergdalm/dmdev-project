package com.sergdalm.dao;

import com.sergdalm.entity.Address;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class AddressRepository extends RepositoryBase<Address, Integer> {

    public AddressRepository(EntityManager entityManager) {
        super(Address.class, entityManager);
    }
}
