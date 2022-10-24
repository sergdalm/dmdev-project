package com.sergdalm.integration.entity;

import com.sergdalm.EntityUtil;
import com.sergdalm.config.BeanProvider;
import com.sergdalm.entity.Service;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class ServiceIT {

    private final SessionFactory sessionFactory = BeanProvider.getSessionFactory();

    @Test
    void shouldCreateAndGetService() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        Service givenService = EntityUtil.getService();

        session.persist(givenService);
        Integer id = givenService.getId();
        session.flush();
        session.clear();

        Service actualService = session.get(Service.class, id);

        assertEquals(givenService, actualService);

        session.getTransaction().rollback();
    }

    @Test
    void shouldCreateAndUpdateService() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        Service givenService = EntityUtil.getService();

        session.persist(givenService);
        Integer id = givenService.getId();

        session.flush();
        session.clear();

        givenService.setDescription("This is the most relaxing massage");
        session.update(givenService);

        session.flush();
        session.clear();

        Service actualService = session.get(Service.class, id);

        assertEquals(givenService, actualService);

        session.getTransaction().rollback();
    }

    @Test
    void shouldCreateAndDeleteService() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        Service givenService = EntityUtil.getService();

        session.persist(givenService);
        Integer id = givenService.getId();

        session.flush();
        session.clear();

        Service savedService = session.get(Service.class, id);

        session.delete(savedService);

        session.flush();
        session.clear();

        Service actualService = session.get(Service.class, id);

        assertNull(actualService);

        session.getTransaction().rollback();

    }
}
