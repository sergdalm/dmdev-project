package com.sergdalm.integration.dao;

import com.sergdalm.EntityUtil;
import com.sergdalm.config.BeanProvider;
import com.sergdalm.dao.DateAndTime;
import com.sergdalm.dao.SpecialistAvailableTimeRepository;
import com.sergdalm.entity.Address;
import com.sergdalm.entity.SpecialistAvailableTime;
import com.sergdalm.entity.User;
import com.sergdalm.filter.SpecialistAvailableTimeFilter;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class SpecialistAvailableTimeRepositoryIT {

    private final SessionFactory sessionFactory = BeanProvider.getSessionFactory();
    private final SpecialistAvailableTimeRepository specialistAvailableTimeRepository = BeanProvider.getSpecialistAvailableTimeRepository();

    @Test
    void findById() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        User specialist = EntityUtil.getUserSpecialist();
        Address address = EntityUtil.getAddress();
        session.persist(specialist);
        session.persist(address);
        SpecialistAvailableTime specialistAvailableTime = EntityUtil.getSpecialistAvailableTime();
        specialistAvailableTime.setSpecialist(specialist);
        specialistAvailableTime.setAddress(address);
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
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        User specialist = EntityUtil.getUserSpecialist();
        Address address = EntityUtil.getAddress();
        session.persist(specialist);
        session.persist(address);
        SpecialistAvailableTime specialistAvailableTime = EntityUtil.getSpecialistAvailableTime();
        specialistAvailableTime.setSpecialist(specialist);
        specialistAvailableTime.setAddress(address);
        specialistAvailableTimeRepository.save(specialistAvailableTime);
        session.flush();
        session.clear();

        List<SpecialistAvailableTime> actualList = specialistAvailableTimeRepository.findAll();

        assertThat(actualList).hasSize(1);
        assertThat(actualList).contains(specialistAvailableTime);

        session.getTransaction().rollback();
    }

    @Test
    void update() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        User specialist = EntityUtil.getUserSpecialist();
        Address address = EntityUtil.getAddress();
        session.persist(specialist);
        session.persist(address);
        SpecialistAvailableTime specialistAvailableTime = EntityUtil.getSpecialistAvailableTime();
        specialistAvailableTime.setSpecialist(specialist);
        specialistAvailableTime.setAddress(address);
        specialistAvailableTimeRepository.save(specialistAvailableTime);
        session.flush();
        session.clear();
        DateAndTime newDateAndTime = new DateAndTime(LocalDateTime.of(2022, 10, 20, 18, 0));
        specialistAvailableTime.setDateAndTime(newDateAndTime);
        specialistAvailableTimeRepository.update(specialistAvailableTime);
        session.flush();
        session.clear();

        Optional<SpecialistAvailableTime> actualSpecialistAvailableTime = specialistAvailableTimeRepository.findById(specialistAvailableTime.getId());

        assertThat(actualSpecialistAvailableTime).isPresent();
        assertEquals(specialistAvailableTime, actualSpecialistAvailableTime.get());
        assertEquals(newDateAndTime, actualSpecialistAvailableTime.get().getDateAndTime());

        session.getTransaction().rollback();
    }

    @Test
    void delete() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        User specialist = EntityUtil.getUserSpecialist();
        Address address = EntityUtil.getAddress();
        session.persist(specialist);
        session.persist(address);
        SpecialistAvailableTime specialistAvailableTime = EntityUtil.getSpecialistAvailableTime();
        specialistAvailableTime.setSpecialist(specialist);
        specialistAvailableTime.setAddress(address);
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
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        User specialist = EntityUtil.getUserSpecialist();
        Address address = EntityUtil.getAddress();
        session.persist(specialist);
        session.persist(address);
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
        session.flush();
        session.clear();

        SpecialistAvailableTimeFilter filter1 = SpecialistAvailableTimeFilter.builder()
                .dates(List.of(LocalDate.of(2022, 10, 25)))
                .addressId(null)
                .build();
        SpecialistAvailableTimeFilter filter2 = SpecialistAvailableTimeFilter.builder()
                .specialistId(specialist.getId())
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
