package com.sergdalm.integration.entity;

import com.sergdalm.EntityUtil;
import com.sergdalm.config.BeanProvider;
import com.sergdalm.dao.DateAndTime;
import com.sergdalm.entity.Address;
import com.sergdalm.entity.SpecialistAvailableTime;
import com.sergdalm.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class SpecialistAvailableTimeIT {

    private final SessionFactory sessionFactory = BeanProvider.getSessionFactory();
    private final User specialist = EntityUtil.getUserSpecialist();
    private final Address address = EntityUtil.getAddress();
    private final SpecialistAvailableTime specialistAvailableTime = EntityUtil.getSpecialistAvailableTime();

    @BeforeEach
    void saveEntities() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        session.persist(specialist);
        session.persist(address);

        specialistAvailableTime.setSpecialist(specialist);
        specialistAvailableTime.setAddress(address);
        session.persist(address);

        session.getTransaction().commit();
    }

    @Test
    void shouldGetSpecialistAvailableTime() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        SpecialistAvailableTime actualSpecialistAvailableTime = session.get(
                SpecialistAvailableTime.class, specialistAvailableTime.getId()
        );

        assertEquals(specialistAvailableTime, actualSpecialistAvailableTime);
        assertEquals(specialist, actualSpecialistAvailableTime.getSpecialist());
        assertEquals(address, actualSpecialistAvailableTime.getAddress());

        session.getTransaction().rollback();
    }

    @Test
    void shouldUpdateSpecialistAvailableTime() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        DateAndTime newDateAndTime = new DateAndTime(LocalDateTime.of(2022, 10, 25, 16, 0));
        specialistAvailableTime.setDateAndTime(newDateAndTime);
        session.update(specialistAvailableTime);

        session.flush();
        session.clear();

        SpecialistAvailableTime actualSpecialistAvailableTime = session.get(
                SpecialistAvailableTime.class, specialistAvailableTime.getId()
        );

        assertEquals(specialistAvailableTime, actualSpecialistAvailableTime);
        assertEquals(newDateAndTime, actualSpecialistAvailableTime.getDateAndTime());

        session.getTransaction().rollback();
    }

    @Test
    void shouldDeleteSpecialistAvailableTime() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        session.delete(specialistAvailableTime);

        session.flush();
        session.clear();

        SpecialistAvailableTime actualSpecialistAvailableTime =
                session.get(SpecialistAvailableTime.class, specialistAvailableTime.getId());

        assertNull(actualSpecialistAvailableTime);

        session.getTransaction().rollback();
    }

    @Test
    void shouldDeleteSpecialistAvailableTimeWhenDeletingSpecialist() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        session.delete(specialist);
        session.flush();
        session.clear();

        SpecialistAvailableTime actualSpecialistAvailableTime =
                session.get(SpecialistAvailableTime.class, specialistAvailableTime.getId());

        assertNull(actualSpecialistAvailableTime);

        session.getTransaction().rollback();
    }

    @Test
    void shouldDeleteSpecialistAvailableTimeWhenDeletingAddress() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        session.delete(address);
        session.flush();
        session.clear();

        SpecialistAvailableTime actualSpecialistAvailableTime =
                session.get(SpecialistAvailableTime.class, specialistAvailableTime.getId());

        assertNull(actualSpecialistAvailableTime);

        session.getTransaction().rollback();
    }

    @AfterEach
    void cleanDataBse() {
        Session session = sessionFactory.getCurrentSession();
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
