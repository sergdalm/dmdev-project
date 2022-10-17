package com.sergdalm.integration.entity;

import com.sergdalm.EntityUtil;
import com.sergdalm.entity.Address;
import com.sergdalm.util.HibernateTestUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class AddressIT {

    private static SessionFactory sessionFactory;

    @BeforeAll
    static void setSessionFactory() {
        sessionFactory = HibernateTestUtil.buildSessionFactory();
    }

    @Test
    void shouldCreateAndGetAddress() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            Address givenAddress = EntityUtil.getAddress();

            session.persist(givenAddress);
            Integer id = givenAddress.getId();
            session.flush();
            session.clear();

            Address actualAddress = session.get(Address.class, id);

            assertEquals(givenAddress, actualAddress);

            session.getTransaction().rollback();
        }
    }

    @Test
    void shouldCreateAndUpdateAddress() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            Address givenAddress = EntityUtil.getAddress();

            session.persist(givenAddress);
            Integer id = givenAddress.getId();

            session.flush();
            session.clear();

            givenAddress.setDescription("Lomonosovo 16");
            session.update(givenAddress);

            session.flush();
            session.clear();

            Address actualAddress = session.get(Address.class, id);

            assertEquals(givenAddress, actualAddress);

            session.getTransaction().rollback();
        }
    }

    @Test
    void shouldCreateAndDeleteAddress() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            Address givenAddress = EntityUtil.getAddress();

            session.persist(givenAddress);
            Integer id = givenAddress.getId();

            session.flush();
            session.clear();

            Address savedAddress = session.get(Address.class, id);

            session.delete(savedAddress);

            session.flush();
            session.clear();

            Address actualAddress = session.get(Address.class, id);

            assertNull(actualAddress);

            session.getTransaction().rollback();
        }
    }

    @AfterAll
    static void closeSessionFactory() {
        sessionFactory.close();
    }
}
