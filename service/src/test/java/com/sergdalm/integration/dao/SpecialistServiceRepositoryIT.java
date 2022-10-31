package com.sergdalm.integration.dao;

import com.sergdalm.EntityUtil;
import com.sergdalm.dao.SpecialistServiceRepository;
import com.sergdalm.entity.Service;
import com.sergdalm.entity.ServiceName;
import com.sergdalm.entity.SpecialistService;
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
class SpecialistServiceRepositoryIT {

    private final EntityManager entityManager;
    private final SpecialistServiceRepository specialistServiceRepository;

    @Test
    void saveAndFindById() {
        Service service = EntityUtil.getService();
        User specialist = EntityUtil.getUserSpecialist();
        entityManager.persist(service);
        entityManager.persist(specialist);
        SpecialistService specialistService = EntityUtil.getSpecialistService();
        specialistService.setSpecialist(specialist);
        specialistService.setService(service);
        specialistServiceRepository.save(specialistService);
        entityManager.flush();
        entityManager.clear();

        Optional<SpecialistService> actualOptionalSpecialistService = specialistServiceRepository.findById(specialistService.getId());

        assertThat(actualOptionalSpecialistService).isPresent();
        assertEquals(specialistService, actualOptionalSpecialistService.get());
    }

    @Test
    void findAll() {
        Service service1 = EntityUtil.getService();
        Service service2 = Service.builder()
                .name(ServiceName.HONEY_MASSAGE)
                .description("Good for health")
                .build();
        SpecialistService specialistService1 = EntityUtil.getSpecialistService();
        SpecialistService specialistService2 = SpecialistService.builder()
                .price(1500)
                .lengthMin(90)
                .build();
        User specialist = EntityUtil.getUserSpecialist();
        entityManager.persist(service1);
        entityManager.persist(service2);
        entityManager.persist(specialist);
        specialistService1.setSpecialist(specialist);
        specialistService2.setSpecialist(specialist);
        specialistService1.setService(service1);
        specialistService2.setService(service2);
        entityManager.persist(specialistService1);
        entityManager.persist(specialistService2);
        entityManager.flush();
        entityManager.clear();

        List<SpecialistService> actualSpecialistServiceList = specialistServiceRepository.findAll();

        assertThat(actualSpecialistServiceList).hasSize(2);
        assertThat(actualSpecialistServiceList).contains(specialistService1, specialistService2);
    }

    @Test
    void update() {
        Service service = EntityUtil.getService();
        User specialist = EntityUtil.getUserSpecialist();
        entityManager.persist(service);
        entityManager.persist(specialist);
        SpecialistService specialistService = EntityUtil.getSpecialistService();
        specialistService.setSpecialist(specialist);
        specialistService.setService(service);
        entityManager.persist(specialistService);
        entityManager.flush();
        entityManager.clear();

        Integer newPrice = 3000;
        specialistService.setPrice(newPrice);
        specialistServiceRepository.update(specialistService);
        Optional<SpecialistService> actualOptionalSpecialistService = specialistServiceRepository.findById(specialistService.getId());

        assertThat(actualOptionalSpecialistService).isPresent();
        assertEquals(specialistService, actualOptionalSpecialistService.get());
        assertEquals(newPrice, actualOptionalSpecialistService.get().getPrice());
    }

    @Test
    void delete() {
        Service service = EntityUtil.getService();
        User specialist = EntityUtil.getUserSpecialist();
        UserInfo userInfo = EntityUtil.getSpecialistUserInfo();
        entityManager.persist(service);
        entityManager.persist(specialist);
        userInfo.setUser(specialist);
        entityManager.persist(userInfo);
        SpecialistService specialistService = EntityUtil.getSpecialistService();
        specialistService.setSpecialist(specialist);
        specialistService.setService(service);
        entityManager.persist(specialistService);
        entityManager.flush();
        entityManager.clear();
        SpecialistService savedSpecialistService = entityManager.find(SpecialistService.class, specialistService.getId());

        specialistServiceRepository.delete(savedSpecialistService);
        Optional<SpecialistService> actualOptionalSpecialistService = specialistServiceRepository
                .findById(specialistService.getId());

        assertThat(actualOptionalSpecialistService).isNotPresent();
    }
}
