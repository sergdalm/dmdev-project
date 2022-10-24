package com.sergdalm.integration.dao;

import com.sergdalm.EntityUtil;
import com.sergdalm.config.BeanProvider;
import com.sergdalm.dao.AppointmentRepository;
import com.sergdalm.dao.DateAndTime;
import com.sergdalm.entity.Address;
import com.sergdalm.entity.Appointment;
import com.sergdalm.entity.AppointmentStatus;
import com.sergdalm.entity.Service;
import com.sergdalm.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class AppointmentRepositoryIT {

    private final SessionFactory sessionFactory = BeanProvider.getSessionFactory();
    private final AppointmentRepository appointmentRepository = BeanProvider.getAppointmentRepository();

    @Test
    void saveAndFindById() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        User specialist = EntityUtil.getUserSpecialist();
        User client = EntityUtil.getUserClient();
        Service service = EntityUtil.getService();
        Address address = EntityUtil.getAddress();
        session.persist(specialist);
        session.persist(client);
        session.persist(service);
        session.persist(address);
        Appointment appointment = EntityUtil.getAppointment();
        appointment.setAddress(address);
        appointment.setClient(client);
        appointment.setSpecialist(specialist);
        appointment.setService(service);

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
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        User specialist = EntityUtil.getUserSpecialist();
        User client = EntityUtil.getUserClient();
        Service service = EntityUtil.getService();
        Address address = EntityUtil.getAddress();
        session.persist(specialist);
        session.persist(client);
        session.persist(service);
        session.persist(address);

        Appointment appointment1 = EntityUtil.getAppointment();
        Appointment appointment2 = getAppointmentWithStatusCompletedNotPaid();
        Appointment appointment3 = getAppointmentWithStatusCompletedPaid();
        appointment1.setAddress(address);
        appointment1.setClient(client);
        appointment1.setSpecialist(specialist);
        appointment1.setService(service);
        appointment2.setAddress(address);
        appointment2.setClient(client);
        appointment2.setSpecialist(specialist);
        appointment2.setService(service);
        appointment3.setAddress(address);
        appointment3.setClient(client);
        appointment3.setSpecialist(specialist);
        appointment3.setService(service);
        session.save(appointment1);
        session.save(appointment2);
        session.save(appointment3);
        session.flush();
        session.clear();

        List<Appointment> actualAppointmentList = appointmentRepository.findAll();

        assertThat(actualAppointmentList).hasSize(3);
        assertThat(actualAppointmentList).contains(appointment1, appointment2, appointment3);

        session.getTransaction().rollback();
    }

    @Test
    void update() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        User specialist = EntityUtil.getUserSpecialist();
        User client = EntityUtil.getUserClient();
        Service service = EntityUtil.getService();
        Address address = EntityUtil.getAddress();
        session.persist(specialist);
        session.persist(client);
        session.persist(service);
        session.persist(address);
        Appointment appointment = EntityUtil.getAppointment();
        appointment.setAddress(address);
        appointment.setClient(client);
        appointment.setSpecialist(specialist);
        appointment.setService(service);
        session.save(appointment);
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
    void delete() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        User specialist = EntityUtil.getUserSpecialist();
        User client = EntityUtil.getUserClient();
        Service service = EntityUtil.getService();
        Address address = EntityUtil.getAddress();
        session.persist(specialist);
        session.persist(client);
        session.persist(service);
        session.persist(address);
        Appointment appointment = EntityUtil.getAppointment();
        appointment.setAddress(address);
        appointment.setClient(client);
        appointment.setSpecialist(specialist);
        appointment.setService(service);
        session.save(appointment);
        session.flush();
        session.clear();

        appointmentRepository.delete(appointment);
        Optional<Appointment> actualOptionalAppointment = appointmentRepository.findById(appointment.getId());

        assertThat(actualOptionalAppointment).isNotPresent();

        session.getTransaction().rollback();
    }

    @Test
    void findByStatus() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        User specialist = EntityUtil.getUserSpecialist();
        User client = EntityUtil.getUserClient();
        Service service = EntityUtil.getService();
        Address address = EntityUtil.getAddress();
        session.persist(specialist);
        session.persist(client);
        session.persist(service);
        session.persist(address);
        Appointment appointmentNotPaid = getAppointmentWithStatusCompletedNotPaid();
        Appointment appointmentPaid = getAppointmentWithStatusCompletedPaid();
        appointmentNotPaid.setAddress(address);
        appointmentNotPaid.setClient(client);
        appointmentNotPaid.setSpecialist(specialist);
        appointmentNotPaid.setService(service);
        session.save(appointmentNotPaid);
        appointmentPaid.setAddress(address);
        appointmentPaid.setClient(client);
        appointmentPaid.setSpecialist(specialist);
        appointmentPaid.setService(service);
        session.save(appointmentPaid);
        session.flush();
        session.clear();

        List<AppointmentStatus> statuses = List.of(
                AppointmentStatus.COMPLETED_NOT_PAID);

        List<Appointment> actualAppointmentList = appointmentRepository.findByStatus(statuses);

        assertThat(actualAppointmentList).hasSize(1);
        assertThat(actualAppointmentList).contains(appointmentNotPaid);
        assertThat(actualAppointmentList).doesNotContain(appointmentPaid);

        session.getTransaction().rollback();
    }

    private Appointment getAppointmentWithStatusCompletedPaid() {
        return Appointment.builder()
                .dateAndTime(new DateAndTime(LocalDateTime.of(2022, 10, 3, 12, 0)))
                .price(1000)
                .lengthMin(60)
                .status(AppointmentStatus.COMPLETED_PAID)
                .build();
    }

    private Appointment getAppointmentWithStatusCompletedNotPaid() {
        return Appointment.builder()
                .dateAndTime(new DateAndTime(LocalDateTime.of(2022, 10, 3, 13, 0)))
                .price(1000)
                .lengthMin(60)
                .status(AppointmentStatus.COMPLETED_NOT_PAID)
                .build();
    }
}
