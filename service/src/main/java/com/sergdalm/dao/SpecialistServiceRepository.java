package com.sergdalm.dao;

import com.sergdalm.entity.SpecialistService;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

@Repository
public class SpecialistServiceRepository extends RepositoryBase<SpecialistService, Integer> {

    public SpecialistServiceRepository(SessionFactory sessionFactory) {
        super(SpecialistService.class, sessionFactory);
    }
}
