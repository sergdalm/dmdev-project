package com.sergdalm.integration.dao;

import com.sergdalm.EntityUtil;
import com.sergdalm.dao.ServiceSaleRepository;
import com.sergdalm.entity.Address;
import com.sergdalm.entity.Service;
import com.sergdalm.entity.ServiceSale;
import com.sergdalm.entity.SpecialistService;
import com.sergdalm.entity.User;
import com.sergdalm.integration.IntegrationTestBase;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@RequiredArgsConstructor
class ServiceSaleRepositoryIT extends IntegrationTestBase {

    private final EntityManager entityManager;
    private final ServiceSaleRepository serviceSaleRepository;

    @Test
    void saveAndFindById() {
        User specialist = EntityUtil.getSpecialistDmitry();
        Address address = EntityUtil.getAddressNarvskaya();
        Service service = EntityUtil.getServiceClassicMassage();
        SpecialistService specialistService = EntityUtil.getSpecialistService();
        entityManager.persist(specialist);
        entityManager.persist(address);
        entityManager.persist(service);
        specialistService.setSpecialist(specialist);
        specialistService.setService(service);
        entityManager.persist(specialistService);
        ServiceSale serviceSale = EntityUtil.getServiceSale();
        serviceSale.setSpecialistService(specialistService);
        serviceSale.setAddress(address);
        serviceSaleRepository.save(serviceSale);
        entityManager.flush();
        entityManager.clear();

        Optional<ServiceSale> actualOptionalServiceSale = serviceSaleRepository.findById(serviceSale.getId());

        assertThat(actualOptionalServiceSale).isPresent();
        assertEquals(serviceSale, actualOptionalServiceSale.get());
        assertEquals(specialistService, actualOptionalServiceSale.get().getSpecialistService());
        assertEquals(address, actualOptionalServiceSale.get().getAddress());
    }

    @Test
    void findAll() {
        User specialist = EntityUtil.getSpecialistDmitry();
        Address address = EntityUtil.getAddressNarvskaya();
        Service service = EntityUtil.getServiceClassicMassage();
        SpecialistService specialistService = EntityUtil.getSpecialistService();
        entityManager.persist(specialist);
        entityManager.persist(address);
        entityManager.persist(service);
        specialistService.setSpecialist(specialist);
        specialistService.setService(service);
        entityManager.persist(specialistService);
        ServiceSale serviceSale = EntityUtil.getServiceSale();
        serviceSale.setSpecialistService(specialistService);
        serviceSale.setAddress(address);
        serviceSaleRepository.save(serviceSale);
        entityManager.flush();
        entityManager.clear();

        List<ServiceSale> actualServiceSaleList = serviceSaleRepository.findAll();

        assertThat(actualServiceSaleList).hasSize(1);
        assertThat(actualServiceSaleList).contains(serviceSale);
    }

    @Test
    void delete() {
        User specialist = EntityUtil.getSpecialistDmitry();
        Address address = EntityUtil.getAddressNarvskaya();
        Service service = EntityUtil.getServiceClassicMassage();
        SpecialistService specialistService = EntityUtil.getSpecialistService();
        entityManager.persist(specialist);
        entityManager.persist(address);
        entityManager.persist(service);
        specialistService.setSpecialist(specialist);
        specialistService.setService(service);
        entityManager.persist(specialistService);
        ServiceSale serviceSale = EntityUtil.getServiceSale();
        serviceSale.setSpecialistService(specialistService);
        serviceSale.setAddress(address);
        serviceSaleRepository.save(serviceSale);
        entityManager.flush();
        entityManager.clear();

        ServiceSale savedServiceSale = entityManager.find(ServiceSale.class, serviceSale.getId());
        serviceSaleRepository.delete(savedServiceSale);
        Optional<ServiceSale> actualOptionalServiceSale = serviceSaleRepository.findById(serviceSale.getId());

        assertThat(actualOptionalServiceSale).isNotPresent();
    }

    @Test
    void update() {
        User specialist = EntityUtil.getSpecialistDmitry();
        Address address = EntityUtil.getAddressNarvskaya();
        Service service = EntityUtil.getServiceClassicMassage();
        SpecialistService specialistService = EntityUtil.getSpecialistService();
        entityManager.persist(specialist);
        entityManager.persist(address);
        entityManager.persist(service);
        specialistService.setSpecialist(specialist);
        specialistService.setService(service);
        entityManager.persist(specialistService);
        ServiceSale serviceSale = EntityUtil.getServiceSale();
        serviceSale.setSpecialistService(specialistService);
        serviceSale.setAddress(address);
        serviceSaleRepository.save(serviceSale);
        entityManager.flush();
        entityManager.clear();

        Integer newPrice = 2000;
        serviceSale.setSalePrice(newPrice);
        serviceSaleRepository.save(serviceSale);
        entityManager.flush();
        entityManager.clear();
        Optional<ServiceSale> actualOptionServiceSale = serviceSaleRepository.findById(serviceSale.getId());

        assertThat(actualOptionServiceSale).isPresent();
        assertEquals(serviceSale, actualOptionServiceSale.get());
        assertEquals(newPrice, actualOptionServiceSale.get().getSalePrice());
    }
}