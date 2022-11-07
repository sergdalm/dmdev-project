package com.sergdalm.dao;

import com.sergdalm.entity.ServiceSale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceSaleRepository extends JpaRepository<ServiceSale, Integer> {
}
