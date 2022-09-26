package com.sergdalm.integration;

import com.sergdalm.EntityUtil;
import com.sergdalm.entity.Administrator;
import com.sergdalm.entity.UserInformation;
import com.sergdalm.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.Serializable;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AdministratorIT {

    @BeforeEach
    void clearDatabase() {
        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
             Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            session.createSQLQuery("delete from administrator").executeUpdate();

            session.getTransaction().commit();
        }
    }

    @Test
    void shouldSaveAndGetAdministrator() {
        Administrator administrator = EntityUtil.getAdministrator();

        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
             Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            Serializable savedId = session.save(administrator);

            Administrator actualResult = session.get(Administrator.class, savedId);

            session.getTransaction().commit();

            assertThat(actualResult).isNotNull();
            assertEquals(
                    actualResult.getUserInformation().getFirstName(),
                    administrator.getUserInformation().getFirstName()
            );

            assertEquals(
                    actualResult.getUserInformation().getLastName(),
                    administrator.getUserInformation().getLastName()
            );
            assertEquals(
                    actualResult.getUserInformation().getEmail(),
                    administrator.getUserInformation().getEmail()
            );
            assertEquals(
                    actualResult.getUserInformation().getBirthday(),
                    administrator.getUserInformation().getBirthday()
            );
        }
    }

    @Test
    void shouldCreateAndDeleteAdministrator() {
        Administrator administrator = EntityUtil.getAdministrator();

        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
             Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            Serializable savedId = session.save(administrator);

            Administrator savedActualResult = session.get(Administrator.class, savedId);

            assertThat(savedActualResult).isNotNull();

            session.delete(savedActualResult);

            Administrator deletedActualResult = session.get(Administrator.class, savedId);

            assertThat(deletedActualResult).isNull();

            session.getTransaction().commit();
        }
    }

    @Test
    void shouldCreateAndUpdateUser() {
        Administrator administrator = EntityUtil.getAdministrator();

        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
             Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            Serializable savedId = session.save(administrator);

            Administrator savedActualResult = session.get(Administrator.class, savedId);

            UserInformation userInformation = savedActualResult.getUserInformation();
            String newEmail = "admin11@gmail.com";
            userInformation.setEmail(newEmail);

            session.flush();
            session.clear();

            Administrator updatedActualResult = session.get(Administrator.class, savedId);

            assertThat(updatedActualResult).isNotNull();
            assertEquals(updatedActualResult.getUserInformation().getEmail(), newEmail);

            session.getTransaction().commit();
        }
    }
}
