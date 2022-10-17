package com.sergdalm.integration.dao;

import com.sergdalm.EntityUtil;
import com.sergdalm.dao.SpecialistAvailableTimeRepository;
import com.sergdalm.entity.Address;
import com.sergdalm.entity.SpecialistAvailableTime;
import com.sergdalm.entity.User;
import com.sergdalm.filter.SpecialistAvailableTimeFilter;
import com.sergdalm.util.HibernateTestUtil;
import com.sergdalm.util.TestDataImporter;
import lombok.Cleanup;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SpecialistAvailableTimeRepositoryIT {

    private static final SessionFactory SESSION_FACTORY = HibernateTestUtil.buildSessionFactory();
    private static final User SPECIALIST = EntityUtil.getUserSpecialist();
    private static final Address ADDRESS = EntityUtil.getAddress();
    private final SpecialistAvailableTimeRepository specialistAvailableTimeRepository = new SpecialistAvailableTimeRepository(SESSION_FACTORY);

    @BeforeAll
    public static void initDb() {
        TestDataImporter.importData(SESSION_FACTORY);

        @Cleanup Session session = SESSION_FACTORY.getCurrentSession();
        session.beginTransaction();

        session.persist(SPECIALIST);
        session.persist(ADDRESS);

        session.getTransaction().commit();
    }

    @AfterAll
    public static void finish() {
        SESSION_FACTORY.close();
    }

    @Test
    void findById() {
        Session session = SESSION_FACTORY.getCurrentSession();
        session.beginTransaction();

        SpecialistAvailableTime specialistAvailableTime = EntityUtil.getSpecialistAvailableTime();
        specialistAvailableTime.setSpecialist(SPECIALIST);
        specialistAvailableTime.setAddress(ADDRESS);
        specialistAvailableTimeRepository.save(specialistAvailableTime);
        session.flush();
        session.clear();

        Optional<SpecialistAvailableTime> actualSpecialistAvailableTime = specialistAvailableTimeRepository.findById(specialistAvailableTime.getId());

        assertThat(actualSpecialistAvailableTime).isPresent();
        assertEquals(specialistAvailableTime, actualSpecialistAvailableTime.get());

        session.getTransaction().rollback();
    }

    @Test
    void findAll() {
        Session session = SESSION_FACTORY.getCurrentSession();
        session.beginTransaction();

        List<SpecialistAvailableTime> actualList = specialistAvailableTimeRepository.findAll();
        Set<LocalDate> actualDateList = actualList.stream()
                .map(SpecialistAvailableTime::getDate)
                .collect(Collectors.toSet());
        Set<LocalTime> actualTimeList = actualList.stream()
                .map(SpecialistAvailableTime::getTime)
                .collect(Collectors.toSet());

        assertThat(actualDateList).hasSize(2);
        assertThat(actualTimeList).hasSize(9);
        assertThat(actualDateList).contains(
                LocalDate.of(2022, 10, 15),
                LocalDate.of(2022, 10, 16));
        assertThat(actualTimeList).contains(
                LocalTime.of(12, 0),
                LocalTime.of(13, 0),
                LocalTime.of(14, 0),
                LocalTime.of(15, 0),
                LocalTime.of(16, 0),
                LocalTime.of(17, 0),
                LocalTime.of(18, 0),
                LocalTime.of(19, 0),
                LocalTime.of(20, 0));

        session.getTransaction().rollback();
    }

    @Test
    void update() {
        Session session = SESSION_FACTORY.getCurrentSession();
        session.beginTransaction();

        SpecialistAvailableTime specialistAvailableTime = EntityUtil.getSpecialistAvailableTime();
        specialistAvailableTime.setSpecialist(SPECIALIST);
        specialistAvailableTime.setAddress(ADDRESS);
        specialistAvailableTimeRepository.save(specialistAvailableTime);
        session.flush();
        session.clear();
        specialistAvailableTime.setTime(LocalTime.of(19, 0));
        specialistAvailableTimeRepository.update(specialistAvailableTime);
        session.flush();
        session.clear();

        Optional<SpecialistAvailableTime> actualSpecialistAvailableTime = specialistAvailableTimeRepository.findById(specialistAvailableTime.getId());

        assertThat(actualSpecialistAvailableTime).isPresent();
        assertEquals(specialistAvailableTime, actualSpecialistAvailableTime.get());

        session.getTransaction().rollback();
    }

    @Test
    void delete() {
        Session session = SESSION_FACTORY.getCurrentSession();
        session.beginTransaction();

        SpecialistAvailableTime specialistAvailableTime = EntityUtil.getSpecialistAvailableTime();
        specialistAvailableTime.setSpecialist(SPECIALIST);
        specialistAvailableTime.setAddress(ADDRESS);
        specialistAvailableTimeRepository.save(specialistAvailableTime);
        session.flush();
        session.clear();
        specialistAvailableTimeRepository.delete(specialistAvailableTime);

        Optional<SpecialistAvailableTime> actualSpecialistAvailableTime = specialistAvailableTimeRepository.findById(specialistAvailableTime.getId());

        assertThat(actualSpecialistAvailableTime).isNotPresent();

        session.getTransaction().rollback();
    }

    @Test
    void findByFilter() {
        Session session = SESSION_FACTORY.getCurrentSession();
        session.beginTransaction();

        SpecialistAvailableTime specialistAvailableTime1 = SpecialistAvailableTime.builder()
                .date(LocalDate.of(2022, 10, 25))
                .time(LocalTime.of(12, 0))
                .build();
        SpecialistAvailableTime specialistAvailableTime2 = SpecialistAvailableTime.builder()
                .date(LocalDate.of(2022, 10, 30))
                .time(LocalTime.of(12, 0))
                .build();
        specialistAvailableTime1.setSpecialist(SPECIALIST);
        specialistAvailableTime1.setAddress(ADDRESS);
        specialistAvailableTimeRepository.save(specialistAvailableTime1);
        specialistAvailableTime1.setSpecialist(SPECIALIST);
        specialistAvailableTime2.setSpecialist(SPECIALIST);
        specialistAvailableTime2.setAddress(ADDRESS);
        specialistAvailableTimeRepository.save(specialistAvailableTime2);
        session.flush();
        session.clear();

        SpecialistAvailableTimeFilter filter1 = SpecialistAvailableTimeFilter.builder()
                .dates(List.of(LocalDate.of(2022, 10, 25)))
                .addressId(null)
                .build();
        SpecialistAvailableTimeFilter filter2 = SpecialistAvailableTimeFilter.builder()
                .specialistId(SPECIALIST.getId())
                .times(List.of(LocalTime.of(12, 0)))
                .dates(Collections.emptyList())
                .build();

        List<SpecialistAvailableTime> actualList1 = specialistAvailableTimeRepository.findByFilter(filter1);
        List<SpecialistAvailableTime> actualList2 = specialistAvailableTimeRepository.findByFilter(filter2);

        assertThat(actualList1).hasSize(1);
        assertThat(actualList2).hasSize(2);
        assertThat(actualList1).contains(specialistAvailableTime1);
        assertThat(actualList2).contains(specialistAvailableTime1, specialistAvailableTime2);

        session.getTransaction().rollback();
    }
}
