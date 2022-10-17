package com.sergdalm.integration.entity;

import com.sergdalm.EntityUtil;
import com.sergdalm.entity.Address;
import com.sergdalm.entity.Appointment;
import com.sergdalm.entity.Service;
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

public class AppointmentIT {

    private static SessionFactory sessionFactory;

    private final User specialist = EntityUtil.getUserSpecialist();
    private final User client = EntityUtil.getUserClient();
    private final Service service = EntityUtil.getService();
    private final Address address = EntityUtil.getAddress();
    private final Appointment appointment = EntityUtil.getAppointment();

    @BeforeAll
    static void setUp() {
        sessionFactory = HibernateTestUtil.buildSessionFactory();
    }

    @BeforeEach
    void saveEntities() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            session.persist(specialist);
            session.persist(client);
            session.persist(service);
            session.persist(address);

            appointment.setService(service);
            appointment.setSpecialist(specialist);
            appointment.setAddress(address);
            appointment.setClient(client);

            session.persist(appointment);

            session.getTransaction().commit();
        }
    }

    @Test
    void shouldGetAppointment() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            Appointment actualAppointment = session.get(
                    Appointment.class, appointment.getId()
            );

            assertEquals(appointment, actualAppointment);
            assertEquals(specialist, actualAppointment.getSpecialist());
            assertEquals(address, actualAppointment.getAddress());
            assertEquals(client, actualAppointment.getClient());

            session.getTransaction().rollback();
        }
    }

    @Test
    void shouldUpdateAppointment() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            appointment.setPrice(2500);
            session.update(appointment);

            session.flush();
            session.clear();

            Appointment actualAppointment = session.get(
                    Appointment.class, appointment.getId()
            );

            assertEquals(appointment, actualAppointment);

            session.getTransaction().rollback();
        }
    }

    @Test
    void shouldDeleteAppointment() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            session.delete(appointment);

            session.flush();
            session.clear();

            Appointment actualAppointment =
                    session.get(Appointment.class, appointment.getId());

            assertNull(actualAppointment);

            session.getTransaction().rollback();
        }
    }

    @Test
    void shouldDeleteAppointmentWhenDeletingSpecialist() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            session.delete(specialist);

            session.flush();
            session.clear();

            Appointment actualAppointment =
                    session.get(Appointment.class, appointment.getId());

            assertNull(actualAppointment);

            session.getTransaction().rollback();
        }
    }

    @Test
    void shouldDeleteAppointmentWhenDeletingAddress() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            session.delete(address);

            session.flush();
            session.clear();

            Appointment actualAppointment =
                    session.get(Appointment.class, appointment.getId());

            assertNull(actualAppointment);

            session.getTransaction().rollback();
        }
    }

    @Test
    void shouldDeleteAppointmentWhenDeletingService() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            session.delete(service);

            session.flush();
            session.clear();

            Appointment actualAppointment =
                    session.get(Appointment.class, appointment.getId());

            assertNull(actualAppointment);

            session.getTransaction().rollback();
        }
    }

    @Test
    void shouldDeleteAppointmentWhenDeletingClient() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            session.delete(client);

            session.flush();
            session.clear();

            Appointment actualAppointment =
                    session.get(Appointment.class, appointment.getId());

            assertNull(actualAppointment);

            session.getTransaction().rollback();
        }
    }

    @AfterEach
    void cleanDataBse() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            session.createSQLQuery("delete from appointment")
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
