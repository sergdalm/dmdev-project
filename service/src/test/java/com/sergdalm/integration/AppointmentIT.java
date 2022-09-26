package com.sergdalm.integration;

import com.sergdalm.EntityUtil;
import com.sergdalm.entity.Appointment;
import com.sergdalm.entity.Client;
import com.sergdalm.entity.Specialist;
import com.sergdalm.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.Serializable;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AppointmentIT {
    @BeforeAll
    static void createSpecialistAndClient() {
        Client client = EntityUtil.getClient();
        Specialist specialist = EntityUtil.getSpecialist();

        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
             Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            session.save(client);
            session.save(specialist);

            session.getTransaction().commit();
        }
    }

    @BeforeEach
    void clearDatabase() {
        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
             Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            session.createSQLQuery("delete from appointment").executeUpdate();

            session.getTransaction().commit();
        }
    }

    @Test
    void shouldSaveAndGetAppointment() {
        Appointment appointment = EntityUtil.getAppointment();

        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
             Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            Serializable savedId = session.save(appointment);

            Appointment actualResult = session.get(Appointment.class, savedId);

            session.getTransaction().commit();

            assertThat(actualResult).isNotNull();
            assertEquals(
                    actualResult.getClientId(),
                    appointment.getClientId()
            );
            assertEquals(
                    actualResult.getDate(),
                    appointment.getDate()
            );
            assertEquals(
                    actualResult.getLengthMin(),
                    appointment.getLengthMin()
            );
            assertEquals(
                    actualResult.getMassageType(),
                    appointment.getMassageType()
            );
        }
    }

    @Test
    void shouldCreateAndDeleteAppointment() {
        Appointment appointment = EntityUtil.getAppointment();
        Client client = EntityUtil.getClient();
        Specialist specialist = EntityUtil.getSpecialist();

        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
             Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            session.save(client);
            session.save(specialist);
            Serializable savedId = session.save(appointment);

            Appointment savedActualResult = session.get(Appointment.class, savedId);

            assertThat(savedActualResult).isNotNull();

            session.delete(savedActualResult);

            Appointment deletedActualResult = session.get(Appointment.class, savedId);

            assertThat(deletedActualResult).isNull();

            session.getTransaction().commit();
        }
    }

    @Test
    void shouldCreateAndUpdateUser() {
        Appointment appointment = EntityUtil.getAppointment();
        Client client = EntityUtil.getClient();
        Specialist specialist = EntityUtil.getSpecialist();

        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
             Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            session.save(client);
            session.save(specialist);
            Serializable savedId = session.save(appointment);

            Appointment savedActualResult = session.get(Appointment.class, savedId);

            LocalTime newStartTime = LocalTime.of(12, 0);
            savedActualResult.setStartTime(newStartTime);


            session.flush();
            session.clear();

            Appointment updatedActualResult = session.get(Appointment.class, savedId);

            assertThat(updatedActualResult).isNotNull();
            assertEquals(updatedActualResult.getStartTime(), newStartTime);

            session.getTransaction().commit();
        }
    }

    @AfterAll
    static void clearSpecialistAndClientDatabase() {
        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
             Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            session.createSQLQuery("delete from specialist").executeUpdate();
            session.createSQLQuery("delete from client").executeUpdate();

            session.getTransaction().commit();
        }
    }
}
