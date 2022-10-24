package com.sergdalm.integration.entity;

import com.sergdalm.EntityUtil;
import com.sergdalm.config.BeanProvider;
import com.sergdalm.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class UserIT {

    private final SessionFactory sessionFactory = BeanProvider.getSessionFactory();

    @Test
    void shouldCreateAndGetUser() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        User givenSpecialist = EntityUtil.getUserSpecialist();
        session.persist(givenSpecialist);
        session.flush();
        session.clear();

        User actualSpecialist = session.get(User.class, givenSpecialist.getId());

        assertEquals(givenSpecialist, actualSpecialist);

        session.getTransaction().rollback();
    }

    @Test
    void shouldCreateAndUpdateUser() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        User givenSpecialist = EntityUtil.getUserSpecialist();
        session.persist(givenSpecialist);
        session.flush();
        session.clear();

        givenSpecialist.setPassword("172939djjd83j");
        session.update(givenSpecialist);
        session.flush();
        session.clear();
        User actualSpecialist = session.get(User.class, givenSpecialist.getId());

        assertEquals(givenSpecialist, actualSpecialist);

        session.getTransaction().rollback();
    }

    @Test
    void shouldCreateAndDeleteUser() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        User givenSpecialist = EntityUtil.getUserSpecialist();

        session.persist(givenSpecialist);
        Integer id = givenSpecialist.getId();
        session.flush();
        session.clear();

        session.delete(givenSpecialist);
        session.flush();
        session.clear();
        User actualSpecialist = session.get(User.class, id);

        assertNull(actualSpecialist);

        session.getTransaction().rollback();
    }
}
