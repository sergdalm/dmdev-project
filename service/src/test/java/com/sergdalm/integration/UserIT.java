package com.sergdalm.integration;

import com.sergdalm.EntityUtil;
import com.sergdalm.entity.User;
import com.sergdalm.util.HibernateTestUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class UserIT {

    private static SessionFactory sessionFactory;

    @BeforeAll
    static void setSessionFactory() {
        sessionFactory = HibernateTestUtil.buildSessionFactory();
    }

    @Test
    void shouldCreateAndGetUser() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            User givenSpecialist = EntityUtil.getUserSpecialist();

            session.persist(givenSpecialist);
            Integer id = givenSpecialist.getId();

            session.flush();
            session.clear();

            User actualSpecialist = session.get(User.class, id);

            assertEquals(givenSpecialist, actualSpecialist);

            session.getTransaction().rollback();
        }
    }

    @Test
    void shouldCreateAndUpdateUser() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            User givenSpecialist = EntityUtil.getUserSpecialist();

            session.persist(givenSpecialist);
            Integer id = givenSpecialist.getId();

            session.flush();
            session.clear();

            givenSpecialist.setPassword("172939djjd83j");
            session.update(givenSpecialist);

            session.flush();
            session.clear();

            User actualSpecialist = session.get(User.class, id);

            assertEquals(givenSpecialist, actualSpecialist);

            session.getTransaction().rollback();
        }
    }

    @Test
    void shouldCreateAndDeleteUser() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            User givenSpecialist = EntityUtil.getUserSpecialist();

            session.persist(givenSpecialist);
            Integer id = givenSpecialist.getId();

            session.flush();
            session.clear();

            User savedSpecialist = session.get(User.class, id);

            session.delete(savedSpecialist);

            session.flush();
            session.clear();

            User actualSpecialist = session.get(User.class, id);

            assertNull(actualSpecialist);

            session.getTransaction().rollback();
        }
    }

    @AfterAll
    static void closeSessionFactory() {
        sessionFactory.close();
    }
}
