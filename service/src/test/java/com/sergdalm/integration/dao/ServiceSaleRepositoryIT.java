package com.sergdalm.integration.dao;

import com.sergdalm.EntityUtil;
import com.sergdalm.config.BeanProvider;
import com.sergdalm.dao.ServiceSaleRepository;
import com.sergdalm.entity.Address;
import com.sergdalm.entity.Service;
import com.sergdalm.entity.ServiceSale;
import com.sergdalm.entity.SpecialistService;
import com.sergdalm.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ServiceSaleRepositoryIT {

    private final SessionFactory sessionFactory = BeanProvider.getSessionFactory();
    private final ServiceSaleRepository serviceSaleRepository = BeanProvider.getServiceSaleRepository();

    @Test
    void saveAndFindById() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        User specialist = EntityUtil.getUserSpecialist();
        Address address = EntityUtil.getAddress();
        Service service = EntityUtil.getService();
        SpecialistService specialistService = EntityUtil.getSpecialistService();
        session.persist(specialist);
        session.persist(address);
        session.persist(service);
        specialistService.setSpecialist(specialist);
        specialistService.setService(service);
        session.persist(specialistService);
        ServiceSale serviceSale = EntityUtil.getServiceSale();
        serviceSale.setSpecialistService(specialistService);
        serviceSale.setAddress(address);
        serviceSaleRepository.save(serviceSale);
        session.flush();
        session.clear();

        Optional<ServiceSale> actualOptionalServiceSale = serviceSaleRepository.findById(serviceSale.getId());

        assertThat(actualOptionalServiceSale).isPresent();
        assertEquals(serviceSale, actualOptionalServiceSale.get());
        assertEquals(specialistService, actualOptionalServiceSale.get().getSpecialistService());
        assertEquals(address, actualOptionalServiceSale.get().getAddress());

        session.getTransaction().rollback();
    }

    @Test
    void findAll() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        User specialist = EntityUtil.getUserSpecialist();
        Address address = EntityUtil.getAddress();
        Service service = EntityUtil.getService();
        SpecialistService specialistService = EntityUtil.getSpecialistService();
        session.persist(specialist);
        session.persist(address);
        session.persist(service);
        specialistService.setSpecialist(specialist);
        specialistService.setService(service);
        session.persist(specialistService);
        ServiceSale serviceSale = EntityUtil.getServiceSale();
        serviceSale.setSpecialistService(specialistService);
        serviceSale.setAddress(address);
        serviceSaleRepository.save(serviceSale);
        session.flush();
        session.clear();

        List<ServiceSale> actualServiceSaleList = serviceSaleRepository.findAll();

        assertThat(actualServiceSaleList).hasSize(1);
        assertThat(actualServiceSaleList).contains(serviceSale);

        session.getTransaction().rollback();
    }

    @Test
    void delete() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        User specialist = EntityUtil.getUserSpecialist();
        Address address = EntityUtil.getAddress();
        Service service = EntityUtil.getService();
        SpecialistService specialistService = EntityUtil.getSpecialistService();
        session.persist(specialist);
        session.persist(address);
        session.persist(service);
        specialistService.setSpecialist(specialist);
        specialistService.setService(service);
        session.persist(specialistService);
        ServiceSale serviceSale = EntityUtil.getServiceSale();
        serviceSale.setSpecialistService(specialistService);
        serviceSale.setAddress(address);
        serviceSaleRepository.save(serviceSale);
        session.flush();
        session.clear();

        serviceSaleRepository.delete(serviceSale);
        Optional<ServiceSale> actualOptionalServiceSale = serviceSaleRepository.findById(serviceSale.getId());

        assertThat(actualOptionalServiceSale).isNotPresent();

        session.getTransaction().rollback();
    }

    @Test
    void update() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        User specialist = EntityUtil.getUserSpecialist();
        Address address = EntityUtil.getAddress();
        Service service = EntityUtil.getService();
        SpecialistService specialistService = EntityUtil.getSpecialistService();
        session.persist(specialist);
        session.persist(address);
        session.persist(service);
        specialistService.setSpecialist(specialist);
        specialistService.setService(service);
        session.persist(specialistService);
        ServiceSale serviceSale = EntityUtil.getServiceSale();
        serviceSale.setSpecialistService(specialistService);
        serviceSale.setAddress(address);
        serviceSaleRepository.save(serviceSale);
        session.flush();
        session.clear();

        Integer newPrice = 2000;
        serviceSale.setSalePrice(newPrice);
        serviceSaleRepository.update(serviceSale);
        session.flush();
        session.clear();
        Optional<ServiceSale> actualOptionServiceSale = serviceSaleRepository.findById(serviceSale.getId());

        assertThat(actualOptionServiceSale).isPresent();
        assertEquals(serviceSale, actualOptionServiceSale.get());
        assertEquals(newPrice, actualOptionServiceSale.get().getSalePrice());

        session.getTransaction().rollback();
    }
}