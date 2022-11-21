package com.sergdalm.dao;

import com.sergdalm.dao.filter.SpecialistFilter;
import com.sergdalm.entity.User;

import java.util.List;

public interface SpecialistSearch {

    List<User> findSpecialistsByFilter(SpecialistFilter specialistFilter);
}
