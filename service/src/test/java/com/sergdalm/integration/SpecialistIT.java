package com.sergdalm.integration;

import com.sergdalm.EntityUtil;
import com.sergdalm.entity.Specialist;
import com.sergdalm.entity.UserInformation;
import com.sergdalm.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.Serializable;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SpecialistIT {
    @BeforeEach
    void clearDatabase() {
        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
             Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            session.createSQLQuery("delete from specialist").executeUpdate();

            session.getTransaction().commit();
        }
    }

    @Test
    void shouldSaveAndGetSpecialist() {
        Specialist specialist = EntityUtil.getSpecialist();

        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
             Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            Serializable savedId = session.save(specialist);

            Specialist actualResult = session.get(Specialist.class, savedId);

            session.getTransaction().commit();

            assertThat(actualResult).isNotNull();
            assertEquals(
                    actualResult.getUserInformation().getFirstName(),
                    specialist.getUserInformation().getFirstName()
            );

            assertEquals(
                    actualResult.getUserInformation().getLastName(),
                    specialist.getUserInformation().getLastName()
            );
            assertEquals(
                    actualResult.getUserInformation().getEmail(),
                    specialist.getUserInformation().getEmail()
            );
            assertEquals(
                    actualResult.getUserInformation().getBirthday(),
                    specialist.getUserInformation().getBirthday()
            );
        }
    }

    @Test
    void shouldCreateAndDeleteSpecialist() {
        Specialist specialist = EntityUtil.getSpecialist();

        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
             Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            Serializable savedId = session.save(specialist);

            Specialist savedActualResult = session.get(Specialist.class, savedId);

            assertThat(savedActualResult).isNotNull();

            session.delete(savedActualResult);

            Specialist deletedActualResult = session.get(Specialist.class, savedId);

            assertThat(deletedActualResult).isNull();

            session.getTransaction().commit();
        }
    }

    @Test
    void shouldCreateAndUpdateUser() {
        Specialist specialist = EntityUtil.getSpecialist();

        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
             Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            Serializable savedId = session.save(specialist);

            Specialist savedActualResult = session.get(Specialist.class, savedId);

            UserInformation userInformation = savedActualResult.getUserInformation();
            String newEmail = "admin11@gmail.com";
            userInformation.setEmail(newEmail);

            session.flush();
            session.clear();

            Specialist updatedActualResult = session.get(Specialist.class, savedId);

            assertThat(updatedActualResult).isNotNull();
            assertEquals(updatedActualResult.getUserInformation().getEmail(), newEmail);

            session.getTransaction().commit();
        }
    }
}
