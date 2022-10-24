package com.sergdalm.integration.dao;

import com.sergdalm.EntityUtil;
import com.sergdalm.config.BeanProvider;
import com.sergdalm.dao.UserInfoRepository;
import com.sergdalm.entity.User;
import com.sergdalm.entity.UserInfo;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserInfoRepositoryIT {

    private final SessionFactory sessionFactory = BeanProvider.getSessionFactory();
    private final UserInfoRepository userInfoRepository = BeanProvider.getUserInfoRepository();

    @Test
    void saveAndFindByIdUser() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        User user = EntityUtil.getUserSpecialist();
        session.save(user);
        UserInfo userInfo = EntityUtil.getSpecialistUserInfo();
        userInfo.setUser(user);
        userInfoRepository.save(userInfo);
        session.flush();
        session.clear();

        Optional<UserInfo> actualOptionalUserInfo = userInfoRepository.findById(user.getId());

        assertThat(actualOptionalUserInfo).isPresent();
        assertEquals(userInfo, actualOptionalUserInfo.get());

        session.getTransaction().rollback();
    }

    @Test
    void findAll() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        User specialist = EntityUtil.getUserSpecialist();
        User client = EntityUtil.getUserClient();
        session.save(specialist);
        session.save(client);
        UserInfo specialistUserInfo = EntityUtil.getSpecialistUserInfo();
        UserInfo clientUserInfo = EntityUtil.getClientUserInfo();
        specialistUserInfo.setUser(specialist);
        clientUserInfo.setUser(client);
        session.save(specialistUserInfo);
        session.flush();
        session.clear();

        List<UserInfo> actualUserInfoList = userInfoRepository.findAll();

        assertThat(actualUserInfoList).isNotEmpty();
        assertThat(actualUserInfoList).hasSize(2);
        assertThat(actualUserInfoList).contains(clientUserInfo, specialistUserInfo);

        session.getTransaction().rollback();
    }

    @Test
    void update() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        User user = EntityUtil.getUserSpecialist();
        session.save(user);
        UserInfo userInfo = EntityUtil.getSpecialistUserInfo();
        userInfo.setUser(user);
        userInfoRepository.save(userInfo);
        session.flush();
        session.clear();

        String newDescription = "Work experience: 3 years";
        userInfo.setDescription(newDescription);
        userInfoRepository.update(userInfo);
        session.flush();
        session.clear();

        Optional<UserInfo> actualOptionalUserInfo = userInfoRepository.findById(user.getId());

        assertThat(actualOptionalUserInfo).isPresent();
        assertEquals(userInfo, actualOptionalUserInfo.get());
        assertEquals(newDescription, actualOptionalUserInfo.get().getDescription());

        session.getTransaction().rollback();
    }

    @Test
    void delete() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        User user = EntityUtil.getUserSpecialist();
        session.save(user);
        UserInfo userInfo = EntityUtil.getSpecialistUserInfo();
        userInfo.setUser(user);
        userInfoRepository.save(userInfo);
        session.flush();
        session.clear();

        userInfoRepository.delete(userInfo);
        session.flush();
        session.clear();
        Optional<UserInfo> actualOptionalUserInfo = userInfoRepository.findById(user.getId());

        assertThat(actualOptionalUserInfo).isNotPresent();

        session.getTransaction().rollback();
    }
}
