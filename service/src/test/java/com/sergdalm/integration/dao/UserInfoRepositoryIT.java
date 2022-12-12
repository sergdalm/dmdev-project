package com.sergdalm.integration.dao;

import com.sergdalm.EntityUtil;
import com.sergdalm.dao.UserInfoRepository;
import com.sergdalm.entity.Role;
import com.sergdalm.entity.User;
import com.sergdalm.entity.UserInfo;
import com.sergdalm.integration.IntegrationTestBase;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@RequiredArgsConstructor
public class UserInfoRepositoryIT extends IntegrationTestBase {

    private final EntityManager entityManager;
    private final UserInfoRepository userInfoRepository;

    @Test
    void saveAndFindByIdUser() {
        User user = User.builder()
                .email("test@gmail.com")
                .mobilePhoneNumber("+7911739204")
                .role(Role.CLIENT)
                .firstName("Test")
                .lastName("Test")
                .password("1234")
                .build();
        entityManager.persist(user);
        UserInfo userInfo = EntityUtil.getSpecialistDmitryUserInfo();
        userInfo.setUser(user);
        entityManager.flush();
        userInfoRepository.save(userInfo);
        entityManager.flush();
        entityManager.clear();

        Optional<UserInfo> actualOptionalUserInfo = userInfoRepository.findById(user.getId());

        assertThat(actualOptionalUserInfo).isPresent();
        assertEquals(userInfo, actualOptionalUserInfo.get());
    }

    @Test
    void findAll() {
        User specialist = EntityUtil.getSpecialistDmitry();
        User client = EntityUtil.getClientSvetlana();
        entityManager.persist(specialist);
        entityManager.persist(client);
        UserInfo specialistUserInfo = EntityUtil.getSpecialistDmitryUserInfo();
        UserInfo clientUserInfo = EntityUtil.getClientSvetlanaUserInfo();
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
        User user = EntityUtil.getSpecialistDmitry();
        entityManager.persist(user);
        UserInfo userInfo = EntityUtil.getSpecialistDmitryUserInfo();
        userInfo.setUser(user);
        entityManager.flush();
        userInfoRepository.save(userInfo);
        entityManager.flush();
        entityManager.clear();

        String newDescription = "Work experience: 3 years";
        userInfo.setDescription(newDescription);
        userInfoRepository.save(userInfo);
        entityManager.flush();
        entityManager.clear();

        Optional<UserInfo> actualOptionalUserInfo = userInfoRepository.findById(user.getId());

        assertThat(actualOptionalUserInfo).isPresent();
        assertEquals(userInfo, actualOptionalUserInfo.get());
        assertEquals(newDescription, actualOptionalUserInfo.get().getDescription());
    }

    @Test
    void shouldDeleteUserInfoWhenDeletingUser() {
        User user = EntityUtil.getSpecialistDmitry();
        entityManager.persist(user);
        UserInfo userInfo = EntityUtil.getSpecialistDmitryUserInfo();
        userInfo.setUser(user);
        entityManager.flush();
        userInfoRepository.save(userInfo);
        entityManager.flush();

        entityManager.remove(user);
        Optional<UserInfo> actualOptionalUserInfo = userInfoRepository.findById(user.getId());

        assertThat(actualOptionalUserInfo).isNotPresent();
    }
}
