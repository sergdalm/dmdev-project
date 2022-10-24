package com.sergdalm.integration.entity;

import com.sergdalm.EntityUtil;
import com.sergdalm.config.BeanProvider;
import com.sergdalm.entity.Service;
import com.sergdalm.entity.SpecialistService;
import com.sergdalm.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class SpecialistServiceIT {

    private final SessionFactory sessionFactory = BeanProvider.getSessionFactory();
    private final User specialist = EntityUtil.getUserSpecialist();
    private final Service service = EntityUtil.getService();
    private final SpecialistService specialistService = EntityUtil.getSpecialistService();

    @BeforeEach
    void saveEntities() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        session.persist(specialist);
        session.persist(service);
        specialistService.setService(service);
        specialistService.setSpecialist(specialist);

        session.persist(specialistService);

        session.getTransaction().commit();
    }

    @Test
    void shouldGetSpecialistService() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        SpecialistService actualSpecialistService = session.get(
                SpecialistService.class, specialistService.getId()
        );

        assertEquals(specialistService, actualSpecialistService);
        assertEquals(specialist, actualSpecialistService.getSpecialist());

        session.getTransaction().rollback();
    }

    @Test
    void shouldUpdateSpecialistService() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        specialistService.setPrice(2500);
        session.update(specialistService);
        session.flush();
        session.clear();

        SpecialistService actualSpecialistService = session.get(
                SpecialistService.class, specialistService.getId()
        );

        assertEquals(specialistService, actualSpecialistService);

        session.getTransaction().rollback();
    }

    @Test
    void shouldDeleteSpecialistService() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        SpecialistService savedSpecialistService = session.get(
                SpecialistService.class, specialistService.getId()
        );

        session.delete(savedSpecialistService);
        session.flush();
        session.clear();

        SpecialistService actualSpecialistService =
                session.get(SpecialistService.class, specialistService.getId());

        assertNull(actualSpecialistService);

        session.getTransaction().rollback();
    }

    @Test
    void shouldDeleteSpecialistServiceWhenDeletingSpecialist() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        session.delete(specialist);
        session.flush();
        session.clear();

        SpecialistService actualSpecialistService =
                session.get(SpecialistService.class, specialistService.getId());

        assertNull(actualSpecialistService);

        session.getTransaction().rollback();
    }

    @AfterEach
    void cleanDataBse() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        session.createSQLQuery("delete from specialist_service")
                .executeUpdate();
        session.createSQLQuery("delete from users")
                .executeUpdate();
        session.createSQLQuery("delete from service")
                .executeUpdate();

        session.getTransaction().commit();
    }
}
