package com.sergdalm.integration.entity;

import com.sergdalm.EntityUtil;
import com.sergdalm.config.BeanProvider;
import com.sergdalm.entity.User;
import com.sergdalm.entity.UserInfo;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class UserInfoIT {

    private final SessionFactory sessionFactory = BeanProvider.getSessionFactory();

    @Test
    void shouldCreateAndGetUser() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        User givenSpecialist = EntityUtil.getUserSpecialist();
        session.persist(givenSpecialist);
        UserInfo givenSpecialistUserInfo = EntityUtil.getSpecialistUserInfo();
        givenSpecialistUserInfo.setUser(givenSpecialist);
        session.persist(givenSpecialistUserInfo);
        session.flush();
        session.clear();

        UserInfo actualSpecialistUserInfo = session.get(UserInfo.class, givenSpecialistUserInfo.getId());

        assertEquals(givenSpecialistUserInfo, actualSpecialistUserInfo);

        session.getTransaction().rollback();
    }

    @Test
    void shouldCreateAndUpdateUser() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        User givenSpecialist = EntityUtil.getUserSpecialist();
        session.persist(givenSpecialist);
        UserInfo givenSpecialistUserInfo = EntityUtil.getSpecialistUserInfo();
        givenSpecialistUserInfo.setUser(givenSpecialist);
        session.persist(givenSpecialistUserInfo);
        session.flush();
        session.clear();

        String newLastName = "Cheremushkin";
        givenSpecialistUserInfo.setLastName(newLastName);
        session.update(givenSpecialist);

        session.flush();
        session.clear();

        UserInfo actualSpecialistUserInfo = session.get(UserInfo.class, givenSpecialistUserInfo.getId());

        assertEquals(givenSpecialistUserInfo, actualSpecialistUserInfo);
        assertEquals(newLastName, actualSpecialistUserInfo.getLastName());

        session.getTransaction().rollback();
    }

    @Test
    void shouldCreateAndDeleteUser() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        User givenSpecialist = EntityUtil.getUserSpecialist();
        session.persist(givenSpecialist);
        UserInfo givenSpecialistUserInfo = EntityUtil.getSpecialistUserInfo();
        givenSpecialistUserInfo.setUser(givenSpecialist);
        session.persist(givenSpecialistUserInfo);
        session.flush();
        session.clear();

        session.delete(givenSpecialistUserInfo);
        session.flush();
        session.clear();

        UserInfo actualSpecialistUserInfo = session.get(UserInfo.class, givenSpecialistUserInfo.getId());

        assertNull(actualSpecialistUserInfo);

        session.getTransaction().rollback();
    }

    @Test
    void shouldDeleteUserInfoWhenDeletingUser() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        User givenSpecialist = EntityUtil.getUserSpecialist();
        session.persist(givenSpecialist);
        UserInfo givenSpecialistUserInfo = EntityUtil.getSpecialistUserInfo();
        givenSpecialistUserInfo.setUser(givenSpecialist);
        session.persist(givenSpecialistUserInfo);
        session.flush();
        session.clear();

        session.delete(givenSpecialist);
        session.flush();
        session.clear();

        UserInfo actualSpecialistUserInfo = session.get(UserInfo.class, givenSpecialistUserInfo.getId());

        assertNull(actualSpecialistUserInfo);

        session.getTransaction().rollback();
    }
}
