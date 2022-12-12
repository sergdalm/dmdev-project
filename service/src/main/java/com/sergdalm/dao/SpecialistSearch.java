package com.sergdalm.dao;

import com.sergdalm.dao.filter.SpecialistFilter;
import com.sergdalm.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SpecialistSearch {
    Page<User> findAll(SpecialistFilter specialistFilter, Pageable page);
}
