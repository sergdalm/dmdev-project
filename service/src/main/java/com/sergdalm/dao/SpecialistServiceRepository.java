package com.sergdalm.dao;

import com.sergdalm.entity.SpecialistService;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class SpecialistServiceRepository extends RepositoryBase<SpecialistService, Integer> {

    public SpecialistServiceRepository(EntityManager entityManager) {
        super(SpecialistService.class, entityManager);
    }
}
