package com.sergdalm.integration.dao;

import com.sergdalm.EntityUtil;
import com.sergdalm.dao.SpecialistAvailableTimeRepository;
import com.sergdalm.entity.Address;
import com.sergdalm.entity.DateAndTime;
import com.sergdalm.entity.SpecialistAvailableTime;
import com.sergdalm.entity.User;
import com.sergdalm.filter.SpecialistAvailableTimeFilter;
import com.sergdalm.integration.IntegrationTestBase;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@RequiredArgsConstructor
class SpecialistAvailableTimeRepositoryIT extends IntegrationTestBase {

    private final EntityManager entityManager;
    private final SpecialistAvailableTimeRepository specialistAvailableTimeRepository;

    @Test
    void findById() {
        User specialist = EntityUtil.getUserSpecialist();
        Address address = EntityUtil.getAddress();
        entityManager.persist(specialist);
        entityManager.persist(address);
        SpecialistAvailableTime specialistAvailableTime = EntityUtil.getSpecialistAvailableTime();
        specialistAvailableTime.setSpecialist(specialist);
        specialistAvailableTime.setAddress(address);
        specialistAvailableTimeRepository.save(specialistAvailableTime);
        entityManager.flush();
        entityManager.clear();

        Optional<SpecialistAvailableTime> actualSpecialistAvailableTime = specialistAvailableTimeRepository.findById(specialistAvailableTime.getId());

        assertThat(actualSpecialistAvailableTime).isPresent();
        assertEquals(specialistAvailableTime, actualSpecialistAvailableTime.get());
    }

    @Test
    void findAll() {
        User specialist = EntityUtil.getUserSpecialist();
        Address address = EntityUtil.getAddress();
        entityManager.persist(specialist);
        entityManager.persist(address);
        SpecialistAvailableTime specialistAvailableTime = EntityUtil.getSpecialistAvailableTime();
        specialistAvailableTime.setSpecialist(specialist);
        specialistAvailableTime.setAddress(address);
        specialistAvailableTimeRepository.save(specialistAvailableTime);
        entityManager.flush();
        entityManager.clear();

        List<SpecialistAvailableTime> actualList = specialistAvailableTimeRepository.findAll();

        assertThat(actualList).hasSize(1);
        assertThat(actualList).contains(specialistAvailableTime);
    }

    @Test
    void update() {
        User specialist = EntityUtil.getUserSpecialist();
        Address address = EntityUtil.getAddress();
        entityManager.persist(specialist);
        entityManager.persist(address);
        SpecialistAvailableTime specialistAvailableTime = EntityUtil.getSpecialistAvailableTime();
        specialistAvailableTime.setSpecialist(specialist);
        specialistAvailableTime.setAddress(address);
        specialistAvailableTimeRepository.save(specialistAvailableTime);
        entityManager.flush();
        entityManager.clear();
        DateAndTime newDateAndTime = new DateAndTime(LocalDateTime.of(2022, 10, 20, 18, 0));
        specialistAvailableTime.setDateAndTime(newDateAndTime);
        specialistAvailableTimeRepository.save(specialistAvailableTime);
        entityManager.flush();
        entityManager.clear();

        Optional<SpecialistAvailableTime> actualSpecialistAvailableTime = specialistAvailableTimeRepository.findById(specialistAvailableTime.getId());

        assertThat(actualSpecialistAvailableTime).isPresent();
        assertEquals(specialistAvailableTime, actualSpecialistAvailableTime.get());
        assertEquals(newDateAndTime, actualSpecialistAvailableTime.get().getDateAndTime());
    }

    @Test
    void delete() {
        User specialist = EntityUtil.getUserSpecialist();
        Address address = EntityUtil.getAddress();
        entityManager.persist(specialist);
        entityManager.persist(address);
        SpecialistAvailableTime specialistAvailableTime = EntityUtil.getSpecialistAvailableTime();
        specialistAvailableTime.setSpecialist(specialist);
        specialistAvailableTime.setAddress(address);
        specialistAvailableTimeRepository.save(specialistAvailableTime);
        entityManager.flush();
        entityManager.clear();

        SpecialistAvailableTime savedSpecialistAvailableTime = entityManager.find(SpecialistAvailableTime.class, specialistAvailableTime.getId());
        specialistAvailableTimeRepository.delete(savedSpecialistAvailableTime);
        Optional<SpecialistAvailableTime> actualSpecialistAvailableTime = specialistAvailableTimeRepository.findById(specialistAvailableTime.getId());

        assertThat(actualSpecialistAvailableTime).isNotPresent();
    }

    @Test
    void findByFilter() {
        User specialist = EntityUtil.getUserSpecialist();
        Address address = EntityUtil.getAddress();
        entityManager.persist(specialist);
        entityManager.persist(address);
        SpecialistAvailableTime specialistAvailableTime1 = SpecialistAvailableTime.builder()
                .dateAndTime(new DateAndTime(LocalDateTime.of(2022, 10, 25, 12, 0)))
                .build();
        SpecialistAvailableTime specialistAvailableTime2 = SpecialistAvailableTime.builder()
                .dateAndTime(new DateAndTime(LocalDateTime.of(2022, 10, 30, 12, 0)))
                .build();
        specialistAvailableTime1.setSpecialist(specialist);
        specialistAvailableTime1.setAddress(address);
        specialistAvailableTime1.setSpecialist(specialist);
        specialistAvailableTime2.setSpecialist(specialist);
        specialistAvailableTime2.setAddress(address);
        specialistAvailableTimeRepository.save(specialistAvailableTime1);
        specialistAvailableTimeRepository.save(specialistAvailableTime2);
        entityManager.flush();
        entityManager.clear();

        SpecialistAvailableTimeFilter filter1 = SpecialistAvailableTimeFilter.builder()
                .dates(List.of(LocalDate.of(2022, 10, 25)))
                .addressId(null)
                .build();
        SpecialistAvailableTimeFilter filter2 = SpecialistAvailableTimeFilter.builder()
                .specialistId(specialist.getId())
                .times(List.of(LocalTime.of(12, 0)))
                .dates(Collections.emptyList())
                .build();

        List<SpecialistAvailableTime> actualList1 = specialistAvailableTimeRepository.findByFilter(filter1, entityManager);
        List<SpecialistAvailableTime> actualList2 = specialistAvailableTimeRepository.findByFilter(filter2, entityManager);

        assertThat(actualList1).hasSize(1);
        assertThat(actualList2).hasSize(2);
        assertThat(actualList1).contains(specialistAvailableTime1);
        assertThat(actualList2).contains(specialistAvailableTime1, specialistAvailableTime2);
    }
}
