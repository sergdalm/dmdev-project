package com.sergdalm.integration.dao;

import com.querydsl.core.Tuple;
import com.sergdalm.EntityUtil;
import com.sergdalm.config.BeanProvider;
import com.sergdalm.dao.DateAndTime;
import com.sergdalm.dao.UserRepository;
import com.sergdalm.entity.Address;
import com.sergdalm.entity.Appointment;
import com.sergdalm.entity.AppointmentStatus;
import com.sergdalm.entity.Service;
import com.sergdalm.entity.ServiceName;
import com.sergdalm.entity.SpecialistService;
import com.sergdalm.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class UserRepositoryIT {

    private final SessionFactory sessionFactory = BeanProvider.getSessionFactory();
    private final UserRepository userRepository = BeanProvider.getUserRepository();

    @Test
    void saveAndFindByIdUser() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        User user = EntityUtil.getUserAdministrator();
        userRepository.save(user);
        session.flush();
        session.clear();

        Optional<User> actualOptionalUser = userRepository.findById(user.getId());

        assertThat(actualOptionalUser).isPresent();
        assertEquals(user, actualOptionalUser.get());

        session.getTransaction().rollback();
    }

    @Test
    void findAll() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        User user1 = EntityUtil.getUserSpecialist();
        User user2 = EntityUtil.getUserClient();
        session.save(user1);
        session.save(user2);
        session.flush();
        session.clear();

        List<User> actualUsers = userRepository.findAll();

        assertThat(actualUsers).isNotEmpty();
        assertThat(actualUsers).hasSize(2);
        assertThat(actualUsers).contains(user1, user2);

        session.getTransaction().rollback();
    }

    @Test
    void update() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        User user = EntityUtil.getUserSpecialist();
        session.save(user);
        session.flush();
        session.clear();

        String newNumber = "+7(911)475-76-13";
        user.setMobilePhoneNumber(newNumber);
        userRepository.update(user);
        session.flush();
        session.clear();
        Optional<User> actualOptionalUser = userRepository.findById(user.getId());

        assertThat(actualOptionalUser).isPresent();
        assertEquals(user, actualOptionalUser.get());
        assertEquals(newNumber, actualOptionalUser.get().getMobilePhoneNumber());

        session.getTransaction().rollback();
    }

    @Test
    void delete() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        User user = EntityUtil.getUserSpecialist();
        session.save(user);
        session.flush();
        session.clear();

        userRepository.delete(user);
        Optional<User> actualOptionalUser = userRepository.findById(user.getId());

        assertThat(actualOptionalUser).isNotPresent();

        session.getTransaction().rollback();
    }

    @Test
    void getSpecialistsWhoHCanDoParticularMassageTypes() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        User specialist = EntityUtil.getUserSpecialist();
        Service service = Service.builder()
                .name(ServiceName.LYMPHATIC_DRAINAGE_MASSAGE)
                .description("Good for losing weight")
                .build();
        session.save(specialist);
        session.save(service);
        SpecialistService specialistService = SpecialistService.builder()
                .lengthMin(90)
                .price(1500)
                .build();
        specialistService.setSpecialist(specialist);
        specialistService.setService(service);
        session.save(specialistService);

        List<ServiceName> services = List.of(ServiceName.LYMPHATIC_DRAINAGE_MASSAGE);
        List<User> actualUsers = userRepository.getUsersByMassageType(services);

        assertThat(actualUsers).hasSize(1);
        assertThat(actualUsers).contains(specialist);

        session.getTransaction().rollback();
    }

    @Test
    void findClientsWhoDidNotPaid() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        Service service = EntityUtil.getService();
        User client = EntityUtil.getUserClient();
        User specialist = EntityUtil.getUserSpecialist();
        Address address = Address.builder()
                .addressName("Stachek 123")
                .description("Near subway Narvsakya")
                .build();
        Appointment appointment = Appointment.builder()
                .dateAndTime(new DateAndTime(LocalDateTime.of(2022, 10, 20, 12, 0)))
                .lengthMin(90)
                .price(2000)
                .status(AppointmentStatus.COMPLETED_NOT_PAID)
                .build();
        session.save(client);
        session.save(specialist);
        session.save(address);
        session.save(service);
        appointment.setClient(client);
        appointment.setSpecialist(specialist);
        appointment.setAddress(address);
        appointment.setService(service);
        session.save(appointment);

        List<Tuple> actualUserAndAmountList = userRepository.findClientsWithAmountWhoDidNotPaid();

        List<User> actualUserList = actualUserAndAmountList.stream()
                .map(it -> it.get(0, User.class))
                .toList();

        assertThat(actualUserAndAmountList).hasSize(1);
        assertThat(actualUserList).contains(client);

        session.getTransaction().rollback();
    }
}

