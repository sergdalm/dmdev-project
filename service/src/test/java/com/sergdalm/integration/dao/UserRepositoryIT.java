package com.sergdalm.integration.dao;

import com.querydsl.core.Tuple;
import com.sergdalm.EntityUtil;
import com.sergdalm.dao.UserRepository;
import com.sergdalm.entity.ServiceName;
import com.sergdalm.entity.User;
import com.sergdalm.util.HibernateTestUtil;
import com.sergdalm.util.TestDataImporter;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserRepositoryIT {

    private static final SessionFactory SESSION_FACTORY = HibernateTestUtil.buildSessionFactory();
    private final UserRepository userRepository = new UserRepository(SESSION_FACTORY);

    @BeforeAll
    public static void initDb() {
        TestDataImporter.importData(SESSION_FACTORY);
    }

    @AfterAll
    public static void finish() {
        SESSION_FACTORY.close();
    }

    @Test
    void saveAndFindByIdUser() {
        Session session = SESSION_FACTORY.getCurrentSession();
        session.beginTransaction();

        User user = EntityUtil.getUserClient();
        userRepository.save(user);
        session.flush();
        session.clear();

        Optional<User> actualOptionalUser = userRepository.findById(user.getId());

        assertThat(actualOptionalUser).isPresent();
        assertEquals(user, actualOptionalUser.get());

        session.getTransaction().rollback();
    }

    @Test
    void getAll() {
        Session session = SESSION_FACTORY.getCurrentSession();
        session.beginTransaction();

        List<User> actualUsers = userRepository.findAll();

        assertThat(actualUsers).isNotEmpty();
        assertThat(actualUsers.stream().map(User::getEmail).toList())
                .contains("dmitry@gmail.com", "natali@gmail.com",
                        "alex@gmail.com", "svetlana@gmail.com",
                        "maria@gmail.com", "kirill@gmail.com",
                        "anna@gmail.com", "elena@gmail.com",
                        "tamara@gmail.com");

        session.getTransaction().rollback();
    }

    @Test
    void update() {
        Session session = SESSION_FACTORY.getCurrentSession();
        session.beginTransaction();

        TestDataImporter.specialistDmitry.setPassword("11123456yg");
        userRepository.update(TestDataImporter.specialistDmitry);

        session.flush();
        session.clear();

        Optional<User> actualOptionalUser = userRepository.findById(TestDataImporter.specialistDmitry.getId());

        assertThat(actualOptionalUser).isPresent();
        assertEquals(TestDataImporter.specialistDmitry, actualOptionalUser.get());

        session.getTransaction().rollback();
    }

    @Test
    void delete() {
        Session session = SESSION_FACTORY.getCurrentSession();
        session.beginTransaction();

        userRepository.delete(TestDataImporter.clientElena);

        Optional<User> actualOptionalUser = userRepository
                .findById(TestDataImporter.clientElena.getId());

        assertThat(actualOptionalUser).isNotPresent();

        session.getTransaction().rollback();
    }

    @Test
    void getSpecialistsWhoHCanDoParticularMassageTypes() {
        Session session = SESSION_FACTORY.getCurrentSession();
        session.beginTransaction();
        List<ServiceName> services = List.of(ServiceName.CUPPING_MASSAGE);
        List<User> actualUsers = userRepository.getUsersByMassageType(services);

        assertEquals(1, actualUsers.size());
        assertThat(actualUsers).contains(TestDataImporter.specialistDmitry);

        session.getTransaction().rollback();
    }

    @Test
    void findClientsWhoDidNotPaid() {
        Session session = SESSION_FACTORY.getCurrentSession();
        session.beginTransaction();

        List<Tuple> actualUserAndAmountList = userRepository.findClientsWithAmountWhoDidNotPaid();

        List<String> actualUserEmails = actualUserAndAmountList.stream()
                .map(it -> it.get(0, User.class))
                .filter(Objects::nonNull)
                .map(User::getEmail)
                .toList();
        List<Integer> amountList = actualUserAndAmountList.stream()
                .map(it -> it.get(1, Integer.class))
                .toList();

        assertThat(actualUserAndAmountList).hasSize(2);
        assertThat(actualUserEmails).contains("elena@gmail.com", "tamara@gmail.com");
        assertThat(amountList).contains(3000, 2000);

        session.getTransaction().rollback();
    }

    @Test
    void findClientsWithNoAppointmentAndJoinedMoreThanThisAmountOfDays() {
        Session session = SESSION_FACTORY.getCurrentSession();
        session.beginTransaction();

        session.getTransaction().rollback();
    }
}

