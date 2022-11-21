package com.sergdalm.integration.dao;

import com.sergdalm.EntityUtil;
import com.sergdalm.dao.AppointmentRepository;
import com.sergdalm.entity.Address;
import com.sergdalm.entity.Appointment;
import com.sergdalm.entity.AppointmentStatus;
import com.sergdalm.entity.Service;
import com.sergdalm.entity.User;
import com.sergdalm.integration.IntegrationTestBase;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@RequiredArgsConstructor
class AppointmentRepositoryIT extends IntegrationTestBase {

    private final EntityManager entityManager;
    private final AppointmentRepository appointmentRepository;

    @Test
    void saveAndFindById() {
        User specialist = EntityUtil.getSpecialistDmitry();
        User client = EntityUtil.getClientSvetlana();
        Service service = EntityUtil.getServiceClassicMassage();
        Address address = EntityUtil.getAddressNarvskaya();
        entityManager.persist(specialist);
        entityManager.persist(client);
        entityManager.persist(service);
        entityManager.persist(address);
        Appointment appointment = EntityUtil.getAppointment();
        appointment.setAddress(address);
        appointment.setClient(client);
        appointment.setSpecialist(specialist);
        appointment.setService(service);

        appointmentRepository.save(appointment);
        entityManager.flush();
        entityManager.clear();
        Optional<Appointment> actualOptionalAppointment = appointmentRepository
                .findById(appointment.getId());

        assertThat(actualOptionalAppointment).isPresent();
        assertEquals(appointment, actualOptionalAppointment.get());
    }

    @Test
    void findAll() {
        User specialist = EntityUtil.getSpecialistDmitry();
        User client = EntityUtil.getClientSvetlana();
        Service service = EntityUtil.getServiceClassicMassage();
        Address address = EntityUtil.getAddressNarvskaya();
        entityManager.persist(specialist);
        entityManager.persist(client);
        entityManager.persist(service);
        entityManager.persist(address);

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
        entityManager.persist(appointment1);
        entityManager.persist(appointment2);
        entityManager.persist(appointment3);
        entityManager.flush();
        entityManager.clear();

        List<Appointment> actualAppointmentList = appointmentRepository.findAll();

        assertThat(actualAppointmentList).hasSize(3);
        assertThat(actualAppointmentList).contains(appointment1, appointment2, appointment3);
    }

    @Test
    void update() {
        User specialist = EntityUtil.getSpecialistDmitry();
        User client = EntityUtil.getClientSvetlana();
        Service service = EntityUtil.getServiceClassicMassage();
        Address address = EntityUtil.getAddressNarvskaya();
        entityManager.persist(specialist);
        entityManager.persist(client);
        entityManager.persist(service);
        entityManager.persist(address);
        Appointment appointment = EntityUtil.getAppointment();
        appointment.setAddress(address);
        appointment.setClient(client);
        appointment.setSpecialist(specialist);
        appointment.setService(service);
        entityManager.persist(appointment);
        entityManager.flush();
        entityManager.clear();

        Integer newPrice = 2500;
        appointment.setPrice(newPrice);
        appointmentRepository.save(appointment);
        entityManager.flush();
        entityManager.clear();
        Optional<Appointment> actualOptionalAppointment = appointmentRepository.findById(appointment.getId());

        assertThat(actualOptionalAppointment).isPresent();
        assertEquals(appointment, actualOptionalAppointment.get());
        assertEquals(newPrice, actualOptionalAppointment.get().getPrice());
    }

    @Test
    void delete() {
        User specialist = EntityUtil.getSpecialistDmitry();
        User client = EntityUtil.getClientSvetlana();
        Service service = EntityUtil.getServiceClassicMassage();
        Address address = EntityUtil.getAddressNarvskaya();
        entityManager.persist(specialist);
        entityManager.persist(client);
        entityManager.persist(service);
        entityManager.persist(address);
        Appointment appointment = EntityUtil.getAppointment();
        appointment.setAddress(address);
        appointment.setClient(client);
        appointment.setSpecialist(specialist);
        appointment.setService(service);
        appointmentRepository.save(appointment);
        entityManager.flush();
        entityManager.clear();

        Appointment savedAppointment = entityManager.find(Appointment.class, appointment.getId());
        appointmentRepository.delete(savedAppointment);
        Optional<Appointment> actualOptionalAppointment = appointmentRepository.findById(appointment.getId());

        assertThat(actualOptionalAppointment).isNotPresent();
    }

    @Test
    void findByStatus() {
        User specialist = EntityUtil.getSpecialistDmitry();
        User client = EntityUtil.getClientSvetlana();
        Service service = EntityUtil.getServiceClassicMassage();
        Address address = EntityUtil.getAddressNarvskaya();
        entityManager.persist(specialist);
        entityManager.persist(client);
        entityManager.persist(service);
        entityManager.persist(address);
        Appointment appointmentNotPaid = getAppointmentWithStatusCompletedNotPaid();
        Appointment appointmentPaid = getAppointmentWithStatusCompletedPaid();
        appointmentNotPaid.setAddress(address);
        appointmentNotPaid.setClient(client);
        appointmentNotPaid.setSpecialist(specialist);
        appointmentNotPaid.setService(service);
        entityManager.persist(appointmentNotPaid);
        appointmentPaid.setAddress(address);
        appointmentPaid.setClient(client);
        appointmentPaid.setSpecialist(specialist);
        appointmentPaid.setService(service);
        entityManager.persist(appointmentPaid);
        entityManager.flush();
        entityManager.clear();

        List<AppointmentStatus> statuses = List.of(
                AppointmentStatus.COMPLETED_NOT_PAID);

        List<Appointment> actualAppointmentList = appointmentRepository.findByStatus(statuses, entityManager);

        assertThat(actualAppointmentList).hasSize(1);
        assertThat(actualAppointmentList).contains(appointmentNotPaid);
        assertThat(actualAppointmentList).doesNotContain(appointmentPaid);
    }

    private Appointment getAppointmentWithStatusCompletedPaid() {
        return Appointment.builder()
                .date(LocalDate.of(2022, 10, 3))
                .startTime(LocalTime.of(12, 0))
                .price(1000)
                .lengthMin(60)
                .status(AppointmentStatus.COMPLETED_PAID)
                .build();
    }

    private Appointment getAppointmentWithStatusCompletedNotPaid() {
        return Appointment.builder()
                .date(LocalDate.of(2022, 10, 3))
                .startTime(LocalTime.of(13, 0))
                .price(1000)
                .lengthMin(60)
                .status(AppointmentStatus.COMPLETED_NOT_PAID)
                .build();
    }
}
