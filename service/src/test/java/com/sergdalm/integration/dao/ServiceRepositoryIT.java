package com.sergdalm.integration.dao;

import com.sergdalm.EntityUtil;
import com.sergdalm.dao.ServiceRepository;
import com.sergdalm.entity.Service;
import com.sergdalm.entity.ServiceName;
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

class ServiceRepositoryIT {

    private static final SessionFactory SESSION_FACTORY = HibernateTestUtil.buildSessionFactory();
    private final ServiceRepository serviceRepository = new ServiceRepository(SESSION_FACTORY);

    @BeforeAll
    public static void initDb() {
        TestDataImporter.importData(SESSION_FACTORY);
    }

    @AfterAll
    public static void finish() {
        SESSION_FACTORY.close();
    }

    @Test
    void SaveAndFindById() {
        Session session = SESSION_FACTORY.getCurrentSession();
        session.beginTransaction();

        Service service = EntityUtil.getService();
        serviceRepository.save(service);
        session.flush();
        session.clear();

        Optional<Service> actualOptionalService = serviceRepository.findById(service.getId());

        assertThat(actualOptionalService).isPresent();
        assertEquals(service, actualOptionalService.get());


        session.getTransaction().rollback();
    }

    @Test
    void findAll() {
        Session session = SESSION_FACTORY.getCurrentSession();
        session.beginTransaction();

        List<Service> actualServiceList = serviceRepository.findAll();
        List<ServiceName> actualServiceNameList = actualServiceList.stream().map(Service::getName).toList();
        List<String> actualServiceDescriptionList = actualServiceList.stream().map(Service::getDescription).toList();

        assertThat(actualServiceList).hasSize(4);
        assertThat(actualServiceNameList).contains(
                ServiceName.CLASSIC_MASSAGE,
                ServiceName.CUPPING_MASSAGE,
                ServiceName.LYMPHATIC_DRAINAGE_MASSAGE,
                ServiceName.HONEY_MASSAGE
        );
        assertThat(actualServiceDescriptionList).contains(
                "Relaxing massage",
                "Helps with pain, inflammation, blood flow, relaxation and well-being",
                "Helps to lose weight",
                "Improves blood circulation in deeper layers of the skin and warms and tones it"
        );

        session.getTransaction().rollback();
    }

    @Test
    void saveAndUpdate() {
        Session session = SESSION_FACTORY.getCurrentSession();
        session.beginTransaction();

        Service service = EntityUtil.getService();
        serviceRepository.save(service);
        session.flush();
        session.clear();
        String newDescription = "Very good for health";
        service.setDescription(newDescription);
        serviceRepository.update(service);
        session.flush();
        session.clear();

        Optional<Service> actualOptionalService = serviceRepository.findById(service.getId());

        assertThat(actualOptionalService).isPresent();
        assertEquals(service, actualOptionalService.get());
        assertEquals(newDescription, actualOptionalService.get().getDescription());

        session.getTransaction().rollback();
    }

    @Test
    void saveAndDelete() {
        Session session = SESSION_FACTORY.getCurrentSession();
        session.beginTransaction();

        Service service = EntityUtil.getService();
        serviceRepository.save(service);
        session.flush();
        session.clear();
        serviceRepository.delete(service);

        Optional<Service> actualOptionalService = serviceRepository.findById(service.getId());

        assertThat(actualOptionalService).isNotPresent();

        session.getTransaction().rollback();
    }
}