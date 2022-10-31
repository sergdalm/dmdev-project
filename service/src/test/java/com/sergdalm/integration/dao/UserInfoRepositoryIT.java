package com.sergdalm.integration.dao;

import com.sergdalm.EntityUtil;
import com.sergdalm.dao.UserInfoRepository;
import com.sergdalm.entity.User;
import com.sergdalm.entity.UserInfo;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@RequiredArgsConstructor
@Transactional
public class UserInfoRepositoryIT {

    private final EntityManager entityManager;
    private final UserInfoRepository userInfoRepository;

    @Test
    void saveAndFindByIdUser() {
        User user = EntityUtil.getUserSpecialist();
        entityManager.persist(user);
        UserInfo userInfo = EntityUtil.getSpecialistUserInfo();
        userInfo.setUser(user);
        userInfoRepository.save(userInfo);
        entityManager.flush();
        entityManager.clear();

        Optional<UserInfo> actualOptionalUserInfo = userInfoRepository.findById(user.getId());

        assertThat(actualOptionalUserInfo).isPresent();
        assertEquals(userInfo, actualOptionalUserInfo.get());
    }

    @Test
    void findAll() {
        User specialist = EntityUtil.getUserSpecialist();
        User client = EntityUtil.getUserClient();
        entityManager.persist(specialist);
        entityManager.persist(client);
        UserInfo specialistUserInfo = EntityUtil.getSpecialistUserInfo();
        UserInfo clientUserInfo = EntityUtil.getClientUserInfo();
        specialistUserInfo.setUser(specialist);
        clientUserInfo.setUser(client);
        entityManager.persist(specialistUserInfo);
        entityManager.flush();
        entityManager.clear();

        List<UserInfo> actualUserInfoList = userInfoRepository.findAll();

        assertThat(actualUserInfoList).isNotEmpty();
        assertThat(actualUserInfoList).hasSize(2);
        assertThat(actualUserInfoList).contains(clientUserInfo, specialistUserInfo);
    }

    @Test
    void update() {
        User user = EntityUtil.getUserSpecialist();
        entityManager.persist(user);
        UserInfo userInfo = EntityUtil.getSpecialistUserInfo();
        userInfo.setUser(user);
        userInfoRepository.save(userInfo);
        entityManager.flush();
        entityManager.clear();

        String newDescription = "Work experience: 3 years";
        userInfo.setDescription(newDescription);
        userInfoRepository.update(userInfo);
        entityManager.flush();
        entityManager.clear();

        Optional<UserInfo> actualOptionalUserInfo = userInfoRepository.findById(user.getId());

        assertThat(actualOptionalUserInfo).isPresent();
        assertEquals(userInfo, actualOptionalUserInfo.get());
        assertEquals(newDescription, actualOptionalUserInfo.get().getDescription());
    }

    @Test
    void shouldDeleteUserInfoWhenDelitingUser() {
        User user = EntityUtil.getUserSpecialist();
        entityManager.persist(user);
        UserInfo userInfo = EntityUtil.getSpecialistUserInfo();
        userInfo.setUser(user);
        userInfoRepository.save(userInfo);

        entityManager.remove(user);
        Optional<UserInfo> actualOptionalUserInfo = userInfoRepository.findById(user.getId());

        assertThat(actualOptionalUserInfo).isNotPresent();
    }
}
