package com.sergdalm.integration;

import com.sergdalm.EntityUtil;
import com.sergdalm.entity.Specialist;
import com.sergdalm.entity.User;
import com.sergdalm.util.HibernateTestUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;

import java.io.Serializable;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SpecialistIT {

    @Test
    void shouldCreateAndGetSpecialist() {
        try (SessionFactory sessionFactory = HibernateTestUtil.buildSessionFactory();
             Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            User givenSpecialist = EntityUtil.getSpecialist();

            session.save(givenSpecialist);
            session.flush();
            session.clear();

            User actualSpecialist = session.get(Specialist.class, 1);

            session.getTransaction().commit();

            assertEquals(givenSpecialist, actualSpecialist);
        }
    }


    @Test
    void shouldCreateAndDeleteSpecialist() {
        try (SessionFactory sessionFactory = HibernateTestUtil.buildSessionFactory();
             Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            User givenSpecialist = EntityUtil.getSpecialist();

            Serializable actualId = session.save(givenSpecialist);

            session.flush();
            session.clear();

            User savedSpecialist = session.get(Specialist.class, actualId);

            session.delete(savedSpecialist);

            session.flush();
            session.clear();

            session.getTransaction().commit();

            User actualSpecialist = session.get(Specialist.class, actualId);

            assertEquals(null, actualSpecialist);
        }
    }
}
