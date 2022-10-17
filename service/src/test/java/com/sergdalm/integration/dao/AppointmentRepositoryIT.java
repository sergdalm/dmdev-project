package com.sergdalm.integration.dao;

import com.sergdalm.EntityUtil;
import com.sergdalm.dao.AppointmentRepository;
import com.sergdalm.entity.Address;
import com.sergdalm.entity.Appointment;
import com.sergdalm.entity.AppointmentStatus;
import com.sergdalm.entity.Service;
import com.sergdalm.entity.User;
import com.sergdalm.util.HibernateTestUtil;
import com.sergdalm.util.TestDataImporter;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AppointmentRepositoryIT {

    private static final SessionFactory SESSION_FACTORY = HibernateTestUtil.buildSessionFactory();
    private static final User SPECIALIST = EntityUtil.getUserSpecialist();
    private static final User CLIENT = EntityUtil.getUserClient();
    private static final Service SERVICE = EntityUtil.getService();
    private static final Address ADDRESS = EntityUtil.getAddress();
    private final AppointmentRepository appointmentRepository = new AppointmentRepository(SESSION_FACTORY);

    @BeforeAll
    public static void initDb() {
        TestDataImporter.importData(SESSION_FACTORY);

        Session session = SESSION_FACTORY.getCurrentSession();
        session.beginTransaction();

        session.persist(SPECIALIST);
        session.persist(CLIENT);
        session.persist(SERVICE);
        session.persist(ADDRESS);

        session.getTransaction().commit();
    }

    @AfterAll
    public static void finish() {
        SESSION_FACTORY.close();
    }


    @Test
    void saveAndFindById() {
        Session session = SESSION_FACTORY.getCurrentSession();
        session.beginTransaction();

        Appointment appointment = EntityUtil.getAppointment();
        appointment.setAddress(ADDRESS);
        appointment.setClient(CLIENT);
        appointment.setSpecialist(SPECIALIST);
        appointment.setService(SERVICE);
        appointmentRepository.save(appointment);
        session.flush();
        session.clear();

        Optional<Appointment> actualOptionalAppointment = appointmentRepository.findById(appointment.getId());

        assertThat(actualOptionalAppointment).isPresent();
        assertEquals(appointment, actualOptionalAppointment.get());

        session.getTransaction().rollback();
    }

    @Test
    void findAll() {
        Session session = SESSION_FACTORY.getCurrentSession();
        session.beginTransaction();

        List<Appointment> actualAppointmentList = appointmentRepository.findAll();
        Integer actualAppointmentPriceSum = actualAppointmentList.stream()
                .mapToInt(Appointment::getPrice)
                .sum();
        Integer actualAppointmentLengthSum = actualAppointmentList.stream()
                .mapToInt(Appointment::getLengthMin)
                .sum();
        List<LocalDate> actualAppointmentDataList = actualAppointmentList.stream()
                .map(Appointment::getDate)
                .toList();
        List<LocalTime> actualAppointmentStartTimeList = actualAppointmentList.stream()
                .map(Appointment::getStartTime)
                .toList();

        assertThat(actualAppointmentList).hasSize(15);
        assertEquals(15000, actualAppointmentPriceSum);
        assertEquals(900, actualAppointmentLengthSum);
        assertThat(actualAppointmentDataList).contains(
                LocalDate.of(2022, 10, 3),
                LocalDate.of(2022, 10, 3),
                LocalDate.of(2022, 10, 3),
                LocalDate.of(2022, 10, 3),
                LocalDate.of(2022, 10, 3),
                LocalDate.of(2022, 10, 3),
                LocalDate.of(2022, 10, 15),
                LocalDate.of(2022, 10, 5),
                LocalDate.of(2022, 10, 5),
                LocalDate.of(2022, 10, 5),
                LocalDate.of(2022, 10, 5),
                LocalDate.of(2022, 10, 5),
                LocalDate.of(2022, 10, 5),
                LocalDate.of(2022, 10, 7),
                LocalDate.of(2022, 10, 11)
        );
        assertThat(actualAppointmentStartTimeList).contains(
                LocalTime.of(12, 0),
                LocalTime.of(13, 0),
                LocalTime.of(14, 0),
                LocalTime.of(15, 0),
                LocalTime.of(16, 0),
                LocalTime.of(17, 0),
                LocalTime.of(17, 0),
                LocalTime.of(12, 0),
                LocalTime.of(13, 0),
                LocalTime.of(14, 0),
                LocalTime.of(15, 0),
                LocalTime.of(16, 0),
                LocalTime.of(16, 0),
                LocalTime.of(17, 0),
                LocalTime.of(17, 0)
        );

        session.getTransaction().rollback();
    }

    @Test
    void saveAndUpdate() {
        Session session = SESSION_FACTORY.getCurrentSession();
        session.beginTransaction();

        Appointment appointment = EntityUtil.getAppointment();
        appointment.setAddress(ADDRESS);
        appointment.setClient(CLIENT);
        appointment.setSpecialist(SPECIALIST);
        appointment.setService(SERVICE);
        appointmentRepository.save(appointment);
        session.flush();
        session.clear();
        Integer newPrice = 2500;
        appointment.setPrice(newPrice);
        appointmentRepository.update(appointment);
        session.flush();
        session.clear();

        Optional<Appointment> actualOptionalAppointment = appointmentRepository.findById(appointment.getId());

        assertThat(actualOptionalAppointment).isPresent();
        assertEquals(appointment, actualOptionalAppointment.get());
        assertEquals(newPrice, actualOptionalAppointment.get().getPrice());

        session.getTransaction().rollback();
    }

    @Test
    void saveAndDelete() {
        Session session = SESSION_FACTORY.getCurrentSession();
        session.beginTransaction();

        Appointment appointment = EntityUtil.getAppointment();
        appointment.setAddress(ADDRESS);
        appointment.setClient(CLIENT);
        appointment.setSpecialist(SPECIALIST);
        appointment.setService(SERVICE);
        appointmentRepository.save(appointment);
        session.flush();
        session.clear();
        appointmentRepository.delete(appointment);

        Optional<Appointment> actualOptionalAppointment = appointmentRepository.findById(appointment.getId());

        assertThat(actualOptionalAppointment).isNotPresent();

        session.getTransaction().rollback();
    }

    @Test
    void findByStatus() {
        Session session = SESSION_FACTORY.getCurrentSession();
        session.beginTransaction();

        List<AppointmentStatus> statuses = List.of(
                AppointmentStatus.CONFIRMED_AND_SCHEDULED, AppointmentStatus.CREATED_NOT_CONFIRMED);

        List<Appointment> actualAppointmentList = appointmentRepository.findByStatus(statuses);
        Stream<LocalDate> actualAppointmentDateList = actualAppointmentList.stream()
                .map(Appointment::getDate);
        Stream<LocalTime> actualAppointmentStartTimeList = actualAppointmentList.stream()
                .map(Appointment::getStartTime);
        Stream<Integer> actualAppointmentPriceList = actualAppointmentList.stream()
                .map(Appointment::getPrice);
        Stream<Integer> actualAppointmentLengthList = actualAppointmentList.stream()
                .map(Appointment::getLengthMin);

        assertThat(actualAppointmentList).hasSize(2);
        assertThat(actualAppointmentDateList).contains(
                LocalDate.of(2022, 10, 15),
                LocalDate.of(2022, 10, 11));
        assertThat(actualAppointmentStartTimeList).contains(
                LocalTime.of(17, 0),
                LocalTime.of(17, 0));
        assertThat(actualAppointmentPriceList).contains(1000, 1000);
        assertThat(actualAppointmentLengthList).contains(60, 60);

        session.getTransaction().rollback();
    }
}
