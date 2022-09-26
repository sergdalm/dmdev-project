package com.sergdalm.integration;

import com.sergdalm.EntityUtil;
import com.sergdalm.entity.Client;
import com.sergdalm.entity.UserInformation;
import com.sergdalm.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.Serializable;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ClientIT {
    @BeforeEach
    void clearDatabase() {
        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
             Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            session.createSQLQuery("delete from client").executeUpdate();

            session.getTransaction().commit();
        }
    }

    @Test
    void shouldSaveAndGetClient() {
        Client client = EntityUtil.getClient();

        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
             Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            Serializable savedId = session.save(client);

            Client actualResult = session.get(Client.class, savedId);

            session.getTransaction().commit();

            assertThat(actualResult).isNotNull();
            assertEquals(
                    actualResult.getUserInformation().getFirstName(),
                    client.getUserInformation().getFirstName()
            );

            assertEquals(
                    actualResult.getUserInformation().getLastName(),
                    client.getUserInformation().getLastName()
            );
            assertEquals(
                    actualResult.getUserInformation().getEmail(),
                    client.getUserInformation().getEmail()
            );
            assertEquals(
                    actualResult.getUserInformation().getBirthday(),
                    client.getUserInformation().getBirthday()
            );
        }
    }

    @Test
    void shouldCreateAndDeleteClient() {
        Client client = EntityUtil.getClient();

        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
             Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            Serializable savedId = session.save(client);

            Client savedActualResult = session.get(Client.class, savedId);

            assertThat(savedActualResult).isNotNull();

            session.delete(savedActualResult);

            Client deletedActualResult = session.get(Client.class, savedId);

            assertThat(deletedActualResult).isNull();

            session.getTransaction().commit();
        }
    }

    @Test
    void shouldCreateAndUpdateUser() {
        Client client = EntityUtil.getClient();

        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
             Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            Serializable savedId = session.save(client);

            Client savedActualResult = session.get(Client.class, savedId);

            UserInformation userInformation = savedActualResult.getUserInformation();
            String newEmail = "admin11@gmail.com";
            userInformation.setEmail(newEmail);

            session.flush();
            session.clear();

            Client updatedActualResult = session.get(Client.class, savedId);

            assertThat(updatedActualResult).isNotNull();
            assertEquals(updatedActualResult.getUserInformation().getEmail(), newEmail);

            session.getTransaction().commit();
        }
    }
}
