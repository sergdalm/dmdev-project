package com.sergdalm.integration.dao;

import com.sergdalm.EntityUtil;
import com.sergdalm.dao.ServiceSaleRepository;
import com.sergdalm.entity.Address;
import com.sergdalm.entity.Service;
import com.sergdalm.entity.ServiceSale;
import com.sergdalm.entity.SpecialistService;
import com.sergdalm.entity.User;
import com.sergdalm.util.HibernateTestUtil;
import com.sergdalm.util.TestDataImporter;
import lombok.Cleanup;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ServiceSaleRepositoryIT {

    private static final SessionFactory SESSION_FACTORY = HibernateTestUtil.buildSessionFactory();
    private static final User SPECIALIST = EntityUtil.getUserSpecialist();
    private static final Address ADDRESS = EntityUtil.getAddress();
    private static final Service SERVICE = EntityUtil.getService();
    private static final SpecialistService SPECIALIST_SERVICE = EntityUtil.getSpecialistService();
    private final ServiceSaleRepository serviceSaleRepository = new ServiceSaleRepository(SESSION_FACTORY);

    @BeforeAll
    public static void initDb() {
        TestDataImporter.importData(SESSION_FACTORY);

        @Cleanup Session session = SESSION_FACTORY.getCurrentSession();
        session.beginTransaction();

        session.persist(SPECIALIST);
        session.persist(ADDRESS);
        session.persist(SERVICE);
        SPECIALIST_SERVICE.setSpecialist(SPECIALIST);
        SPECIALIST_SERVICE.setService(SERVICE);
        session.persist(SPECIALIST_SERVICE);

        session.getTransaction().commit();
    }

    @AfterAll
    public static void finish() {
        SESSION_FACTORY.close();
    }

    @Test
    void saveAndFindById() {
        Session session = SESSION_FACTORY.getCurrentSession();
        session.beginTransaction();

        ServiceSale serviceSale = EntityUtil.getServiceSale();
        serviceSale.setSpecialistService(SPECIALIST_SERVICE);
        serviceSale.setAddress(ADDRESS);
        serviceSaleRepository.save(serviceSale);
        session.flush();
        session.clear();

        Optional<ServiceSale> actualOptionalServiceSale = serviceSaleRepository.findById(serviceSale.getId());

        assertThat(actualOptionalServiceSale).isPresent();
        assertEquals(serviceSale, actualOptionalServiceSale.get());
        assertEquals(SPECIALIST_SERVICE, actualOptionalServiceSale.get().getSpecialistService());
        assertEquals(ADDRESS, actualOptionalServiceSale.get().getAddress());

        session.getTransaction().rollback();
    }

    @Test
    void findAll() {
        Session session = SESSION_FACTORY.getCurrentSession();
        session.beginTransaction();

        List<ServiceSale> actualServiceSaleList = serviceSaleRepository.findAll();
        Stream<Integer> actualServiceSalePriceList = actualServiceSaleList.stream()
                .map(ServiceSale::getSalePrice);
        Stream<Integer> actualServiceSaleDurationDayList = actualServiceSaleList.stream()
                .map(ServiceSale::getDurationDays);
        Stream<LocalDate> actualServiceSaleStartDateList = actualServiceSaleList.stream()
                .map(ServiceSale::getStartDate);

        assertThat(actualServiceSalePriceList).contains(600, 900);
        assertThat(actualServiceSaleDurationDayList).contains(14, 14);
        assertThat(actualServiceSaleStartDateList).contains(
                LocalDate.of(2022, 10, 20),
                LocalDate.of(2022, 10, 20)
        );

        session.getTransaction().rollback();
    }

    @Test
    void delete() {
        Session session = SESSION_FACTORY.getCurrentSession();
        session.beginTransaction();

        ServiceSale serviceSale = EntityUtil.getServiceSale();
        serviceSale.setSpecialistService(SPECIALIST_SERVICE);
        serviceSale.setAddress(ADDRESS);
        serviceSaleRepository.save(serviceSale);
        session.flush();
        session.clear();

        Optional<ServiceSale> actualOptionalServiceSale = serviceSaleRepository.findById(serviceSale.getId());

        assertThat(actualOptionalServiceSale).isPresent();
        assertEquals(serviceSale, actualOptionalServiceSale.get());
        assertEquals(SPECIALIST_SERVICE, actualOptionalServiceSale.get().getSpecialistService());
        assertEquals(ADDRESS, actualOptionalServiceSale.get().getAddress());

        session.getTransaction().rollback();
    }

    @Test
    void update() {
        Session session = SESSION_FACTORY.getCurrentSession();
        session.beginTransaction();

        ServiceSale serviceSale = EntityUtil.getServiceSale();
        serviceSale.setSpecialistService(SPECIALIST_SERVICE);
        serviceSale.setAddress(ADDRESS);
        serviceSaleRepository.save(serviceSale);
        session.flush();
        session.clear();
        serviceSale.setSalePrice(500);
        serviceSaleRepository.update(serviceSale);
        session.flush();
        session.clear();

        Optional<ServiceSale> actualOptionServiceSale = serviceSaleRepository.findById(serviceSale.getId());

        assertThat(actualOptionServiceSale).isPresent();
        assertEquals(serviceSale, actualOptionServiceSale.get());

        session.getTransaction().rollback();
    }
}