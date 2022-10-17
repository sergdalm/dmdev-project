package com.sergdalm.integration.entity;

import com.sergdalm.EntityUtil;
import com.sergdalm.entity.Address;
import com.sergdalm.entity.SpecialistAvailableTime;
import com.sergdalm.entity.User;
import com.sergdalm.util.HibernateTestUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class SpecialistAvailableTimeIT {

    private static SessionFactory sessionFactory;

    private final User specialist = EntityUtil.getUserSpecialist();
    private final Address address = EntityUtil.getAddress();
    private final SpecialistAvailableTime specialistAvailableTime = EntityUtil.getSpecialistAvailableTime();


    @BeforeAll
    static void setUp() {
        sessionFactory = HibernateTestUtil.buildSessionFactory();
    }

    @BeforeEach
    void saveEntities() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            session.persist(specialist);
            session.persist(address);

            specialistAvailableTime.setSpecialist(specialist);
            specialistAvailableTime.setAddress(address);
            session.persist(address);

            session.getTransaction().commit();
        }
    }

    @Test
    void shouldGetSpecialistAvailableTime() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            SpecialistAvailableTime actualSpecialistAvailableTime = session.get(
                    SpecialistAvailableTime.class, specialistAvailableTime.getId()
            );

            assertEquals(specialistAvailableTime, actualSpecialistAvailableTime);
            assertEquals(specialist, actualSpecialistAvailableTime.getSpecialist());
            assertEquals(address, actualSpecialistAvailableTime.getAddress());

            session.getTransaction().rollback();
        }
    }

    @Test
    void shouldUpdateSpecialistAvailableTime() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            specialistAvailableTime.setTime(LocalTime.of(16, 0));
            session.update(specialistAvailableTime);

            session.flush();
            session.clear();

            SpecialistAvailableTime actualSpecialistAvailableTime = session.get(
                    SpecialistAvailableTime.class, specialistAvailableTime.getId()
            );

            assertEquals(specialistAvailableTime, actualSpecialistAvailableTime);

            session.getTransaction().rollback();
        }
    }

    @Test
    void shouldDeleteSpecialistAvailableTime() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            session.delete(specialistAvailableTime);

            session.flush();
            session.clear();

            SpecialistAvailableTime actualSpecialistAvailableTime =
                    session.get(SpecialistAvailableTime.class, specialistAvailableTime.getId());

            assertNull(actualSpecialistAvailableTime);

            session.getTransaction().rollback();
        }
    }

    @Test
    void shouldDeleteSpecialistAvailableTimeWhenDeletingSpecialist() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            session.delete(specialist);

            session.flush();
            session.clear();

            SpecialistAvailableTime actualSpecialistAvailableTime =
                    session.get(SpecialistAvailableTime.class, specialistAvailableTime.getId());

            assertNull(actualSpecialistAvailableTime);

            session.getTransaction().rollback();
        }
    }

    @Test
    void shouldDeleteSpecialistAvailableTimeWhenDeletingAddress() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            session.delete(address);

            session.flush();
            session.clear();

            SpecialistAvailableTime actualSpecialistAvailableTime =
                    session.get(SpecialistAvailableTime.class, specialistAvailableTime.getId());

            assertNull(actualSpecialistAvailableTime);

            session.getTransaction().rollback();
        }
    }


    @AfterEach
    void cleanDataBse() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            session.createSQLQuery("delete from specialist_available_time")
                    .executeUpdate();
            session.createSQLQuery("delete from users")
                    .executeUpdate();
            session.createSQLQuery("delete from service")
                    .executeUpdate();
            session.createSQLQuery("delete from address")
                    .executeUpdate();

            session.getTransaction().commit();
        }
    }

    @AfterAll
    static void closeSessionFactory() {
        sessionFactory.close();
    }
}
