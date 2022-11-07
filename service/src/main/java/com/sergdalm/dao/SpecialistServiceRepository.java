package com.sergdalm.dao;

import com.sergdalm.entity.SpecialistService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpecialistServiceRepository extends JpaRepository<SpecialistService, Integer> {
}
