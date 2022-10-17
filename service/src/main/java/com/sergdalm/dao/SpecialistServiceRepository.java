package com.sergdalm.dao;

import com.sergdalm.entity.SpecialistService;
import org.hibernate.SessionFactory;

public class SpecialistServiceRepository extends RepositoryBase<SpecialistService, Integer> {

    public SpecialistServiceRepository(SessionFactory sessionFactory) {
        super(SpecialistService.class, sessionFactory);
    }
}
