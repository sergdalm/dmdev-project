package com.sergdalm.integration.entity;

import com.sergdalm.EntityUtil;
import com.sergdalm.entity.Service;
import com.sergdalm.entity.SpecialistService;
import com.sergdalm.entity.User;
import com.sergdalm.util.HibernateTestUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class SpecialistServiceIT {

    private static SessionFactory sessionFactory;

    private final User specialist = EntityUtil.getUserSpecialist();
    private final Service service = EntityUtil.getService();
    private final SpecialistService specialistService = EntityUtil.getSpecialistService();

    @BeforeAll
    static void setUp() {
        sessionFactory = HibernateTestUtil.buildSessionFactory();

    }

    @BeforeEach
    void saveEntities() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            session.persist(specialist);
            session.persist(service);

            specialistService.setService(service);
            specialistService.setSpecialist(specialist);

            session.persist(specialistService);

            session.getTransaction().commit();
        }
    }

    @Test
    void shouldGetSpecialistService() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            SpecialistService actualSpecialistService = session.get(
                    SpecialistService.class, specialistService.getId()
            );

            assertEquals(specialistService, actualSpecialistService);
            assertEquals(specialist, actualSpecialistService.getSpecialist());

            session.getTransaction().rollback();
        }
    }

    @Test
    void shouldUpdateSpecialistService() {
        try (Session session = sessionFactory.openSession()) {
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
    }

    @Test
    void shouldDeleteSpecialistService() {
        try (Session session = sessionFactory.openSession()) {
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
    }

    @Test
    void shouldDeleteSpecialistServiceWhenDeletingSpecialist() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            session.delete(specialist);

            session.flush();
            session.clear();

            SpecialistService actualSpecialistService =
                    session.get(SpecialistService.class, specialistService.getId());

            assertNull(actualSpecialistService);

            session.getTransaction().rollback();
        }
    }


    @AfterEach
    void cleanDataBse() {
        try (Session session = sessionFactory.openSession()) {
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

    @AfterAll
    static void closeSessionFactory() {
        sessionFactory.close();
    }
}
