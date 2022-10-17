package com.sergdalm.integration.dao;

import com.sergdalm.EntityUtil;
import com.sergdalm.dao.SpecialistServiceRepository;
import com.sergdalm.entity.Service;
import com.sergdalm.entity.SpecialistService;
import com.sergdalm.entity.User;
import com.sergdalm.util.HibernateTestUtil;
import com.sergdalm.util.TestDataImporter;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SpecialistServiceRepositoryIT {

    private static final SessionFactory SESSION_FACTORY = HibernateTestUtil.buildSessionFactory();
    private final SpecialistServiceRepository specialistServiceRepository = new SpecialistServiceRepository(SESSION_FACTORY);

    @BeforeAll
    public static void initDb() {
        TestDataImporter.importData(SESSION_FACTORY);
    }

    @AfterAll
    public static void finish() {
        SESSION_FACTORY.close();
    }

    @Test
    void saveAndFindById() {
        Session session = SESSION_FACTORY.getCurrentSession();
        session.beginTransaction();

        Service service = EntityUtil.getService();
        User specialist = EntityUtil.getUserSpecialist();
        session.save(service);
        session.save(specialist);
        SpecialistService specialistService = EntityUtil.getSpecialistService();
        specialistService.setSpecialist(specialist);
        specialistService.setService(service);
        specialistServiceRepository.save(specialistService);
        session.flush();
        session.clear();

        Optional<SpecialistService> actualOptionalSpecialistService = specialistServiceRepository.findById(specialistService.getId());

        assertThat(actualOptionalSpecialistService).isPresent();
        assertEquals(specialistService, actualOptionalSpecialistService.get());

        session.getTransaction().rollback();
    }

    @Test
    void findAll() {
        Session session = SESSION_FACTORY.getCurrentSession();
        session.beginTransaction();

        List<SpecialistService> actualSpecialistServiceList = specialistServiceRepository.findAll();
        Integer actualSpecialistServicePriceSum = actualSpecialistServiceList.stream()
                .mapToInt(SpecialistService::getPrice)
                .sum();
        Integer actualSpecialistServiceLengthSum = actualSpecialistServiceList.stream()
                .mapToInt(SpecialistService::getLengthMin)
                .sum();

        assertThat(actualSpecialistServiceList).hasSize(14);
        assertEquals(23500, actualSpecialistServicePriceSum);
        assertEquals(1050, actualSpecialistServiceLengthSum);

        session.getTransaction().rollback();
    }

    @Test
    void saveAndUpdate() {
        Session session = SESSION_FACTORY.getCurrentSession();
        session.beginTransaction();

        Service service = EntityUtil.getService();
        User specialist = EntityUtil.getUserSpecialist();
        session.save(service);
        session.save(specialist);
        SpecialistService specialistService = EntityUtil.getSpecialistService();
        specialistService.setSpecialist(specialist);
        specialistService.setService(service);
        specialistServiceRepository.save(specialistService);
        session.flush();
        session.clear();
        Integer newPrice = 3000;
        specialistService.setPrice(newPrice);
        specialistServiceRepository.update(specialistService);

        Optional<SpecialistService> actualOptionalSpecialistService = specialistServiceRepository.findById(specialistService.getId());

        assertThat(actualOptionalSpecialistService).isPresent();
        assertEquals(specialistService, actualOptionalSpecialistService.get());
        assertEquals(newPrice, actualOptionalSpecialistService.get().getPrice());

        session.getTransaction().rollback();
    }

    @Test
    void saveAndDelete() {
        Session session = SESSION_FACTORY.getCurrentSession();
        session.beginTransaction();

        Service service = EntityUtil.getService();
        User specialist = EntityUtil.getUserSpecialist();
        session.save(service);
        session.save(specialist);
        SpecialistService specialistService = EntityUtil.getSpecialistService();
        specialistService.setSpecialist(specialist);
        specialistService.setService(service);
        specialistServiceRepository.save(specialistService);
        session.flush();
        session.clear();
        specialistServiceRepository.delete(specialistService);

        Optional<SpecialistService> actualOptionalSpecialistService = specialistServiceRepository.findById(specialistService.getId());

        assertThat(actualOptionalSpecialistService).isNotPresent();

        session.getTransaction().rollback();
    }
}
