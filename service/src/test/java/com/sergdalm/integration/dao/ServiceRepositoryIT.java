package com.sergdalm.integration.dao;

import com.sergdalm.EntityUtil;
import com.sergdalm.config.BeanProvider;
import com.sergdalm.dao.ServiceRepository;
import com.sergdalm.entity.Service;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ServiceRepositoryIT {

    private final SessionFactory sessionFactory = BeanProvider.getSessionFactory();
    private final ServiceRepository serviceRepository = BeanProvider.getServiceRepository();

    @Test
    void SaveAndFindById() {
        Session session = sessionFactory.getCurrentSession();
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
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        Service service = EntityUtil.getService();
        serviceRepository.save(service);
        session.flush();
        session.clear();

        List<Service> actualServiceList = serviceRepository.findAll();

        assertThat(actualServiceList).hasSize(1);
        assertThat(actualServiceList).contains(service);

        session.getTransaction().rollback();
    }

    @Test
    void saveAndUpdate() {
        Session session = sessionFactory.getCurrentSession();
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
        Session session = sessionFactory.getCurrentSession();
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