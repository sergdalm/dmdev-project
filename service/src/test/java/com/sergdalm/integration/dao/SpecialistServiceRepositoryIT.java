package com.sergdalm.integration.dao;

import com.sergdalm.EntityUtil;
import com.sergdalm.config.BeanProvider;
import com.sergdalm.dao.SpecialistServiceRepository;
import com.sergdalm.entity.Service;
import com.sergdalm.entity.ServiceName;
import com.sergdalm.entity.SpecialistService;
import com.sergdalm.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class SpecialistServiceRepositoryIT {

    private final SessionFactory sessionFactory = BeanProvider.getSessionFactory();
    private final SpecialistServiceRepository specialistServiceRepository = BeanProvider.getSpecialistServiceRepository();

    @Test
    void saveAndFindById() {
        Session session = sessionFactory.getCurrentSession();
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
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        Service service1 = EntityUtil.getService();
        Service service2 = Service.builder()
                .name(ServiceName.HONEY_MASSAGE)
                .description("Good for health")
                .build();
        SpecialistService specialistService1 = EntityUtil.getSpecialistService();
        SpecialistService specialistService2 = SpecialistService.builder()
                .price(1500)
                .lengthMin(90)
                .build();
        User specialist = EntityUtil.getUserSpecialist();
        session.save(service1);
        session.save(service2);
        session.save(specialist);
        specialistService1.setSpecialist(specialist);
        specialistService2.setSpecialist(specialist);
        specialistService1.setService(service1);
        specialistService2.setService(service2);
        session.save(specialistService1);
        session.save(specialistService2);
        session.flush();
        session.clear();

        List<SpecialistService> actualSpecialistServiceList = specialistServiceRepository.findAll();

        assertThat(actualSpecialistServiceList).hasSize(2);
        assertThat(actualSpecialistServiceList).contains(specialistService1, specialistService2);

        session.getTransaction().rollback();
    }

    @Test
    void update() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        Service service = EntityUtil.getService();
        User specialist = EntityUtil.getUserSpecialist();
        session.save(service);
        session.save(specialist);
        SpecialistService specialistService = EntityUtil.getSpecialistService();
        specialistService.setSpecialist(specialist);
        specialistService.setService(service);
        session.save(specialistService);
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
    void delete() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        Service service = EntityUtil.getService();
        User specialist = EntityUtil.getUserSpecialist();
        session.save(service);
        session.save(specialist);
        SpecialistService specialistService = EntityUtil.getSpecialistService();
        specialistService.setSpecialist(specialist);
        specialistService.setService(service);
        session.save(specialistService);
        session.flush();
        session.clear();

        specialistServiceRepository.delete(specialistService);
        Optional<SpecialistService> actualOptionalSpecialistService = specialistServiceRepository.findById(specialistService.getId());

        assertThat(actualOptionalSpecialistService).isNotPresent();

        session.getTransaction().rollback();
    }
}
