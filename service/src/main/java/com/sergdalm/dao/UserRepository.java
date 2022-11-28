package com.sergdalm.dao;

import com.querydsl.core.Tuple;
import com.sergdalm.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>, SpecialistSearch, ClientSearch, UserSearchFilter {

    List<Tuple> findClientsWithAmountWhoDidNotPaid();
}
