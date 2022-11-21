package com.sergdalm.dao;

import com.querydsl.core.Tuple;

import java.util.List;

public interface ClientSearch {

    List<Tuple> findClientsWithAmountWhoDidNotPaid();
}
