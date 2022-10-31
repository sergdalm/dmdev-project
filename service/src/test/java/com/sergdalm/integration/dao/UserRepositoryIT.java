package com.sergdalm.integration.dao;

import com.querydsl.core.Tuple;
import com.sergdalm.EntityUtil;
import com.sergdalm.dao.UserRepository;
import com.sergdalm.entity.Address;
import com.sergdalm.entity.Appointment;
import com.sergdalm.entity.AppointmentStatus;
import com.sergdalm.entity.DateAndTime;
import com.sergdalm.entity.Service;
import com.sergdalm.entity.ServiceName;
import com.sergdalm.entity.SpecialistService;
import com.sergdalm.entity.User;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@RequiredArgsConstructor
@Transactional
class UserRepositoryIT {

    private final EntityManager entityManager;
    private final UserRepository userRepository;

    @Test
    void saveAndFindByIdUser() {
        User user = EntityUtil.getUserAdministrator();
        userRepository.save(user);
        entityManager.flush();
        entityManager.clear();

        Optional<User> actualOptionalUser = userRepository.findById(user.getId());

        assertThat(actualOptionalUser).isPresent();
        assertEquals(user, actualOptionalUser.get());
    }

    @Test
    void findAll() {
        User user1 = EntityUtil.getUserSpecialist();
        User user2 = EntityUtil.getUserClient();
        entityManager.persist(user1);
        entityManager.persist(user2);
        entityManager.flush();
        entityManager.clear();

        List<User> actualUsers = userRepository.findAll();

        assertThat(actualUsers).isNotEmpty();
        assertThat(actualUsers).hasSize(2);
        assertThat(actualUsers).contains(user1, user2);
    }

    @Test
    void update() {
        User user = EntityUtil.getUserSpecialist();
        entityManager.persist(user);
        entityManager.flush();
        entityManager.clear();

        String newNumber = "+7(911)475-76-13";
        user.setMobilePhoneNumber(newNumber);
        userRepository.update(user);
        entityManager.flush();
        entityManager.clear();
        Optional<User> actualOptionalUser = userRepository.findById(user.getId());

        assertThat(actualOptionalUser).isPresent();
        assertEquals(user, actualOptionalUser.get());
        assertEquals(newNumber, actualOptionalUser.get().getMobilePhoneNumber());
    }

    @Test
    void delete() {
        User user = EntityUtil.getUserSpecialist();
        entityManager.persist(user);

        userRepository.delete(user);
        Optional<User> actualOptionalUser = userRepository.findById(user.getId());

        assertThat(actualOptionalUser).isNotPresent();
    }

    @Test
    void getSpecialistsWhoHCanDoParticularMassageTypes() {
        User specialist = EntityUtil.getUserSpecialist();
        Service service = Service.builder()
                .name(ServiceName.LYMPHATIC_DRAINAGE_MASSAGE)
                .description("Good for losing weight")
                .build();
        entityManager.persist(specialist);
        entityManager.persist(service);
        SpecialistService specialistService = SpecialistService.builder()
                .lengthMin(90)
                .price(1500)
                .build();
        specialistService.setSpecialist(specialist);
        specialistService.setService(service);
        entityManager.persist(specialistService);

        List<ServiceName> services = List.of(ServiceName.LYMPHATIC_DRAINAGE_MASSAGE);
        List<User> actualUsers = userRepository.getUsersByMassageType(services);

        assertThat(actualUsers).hasSize(1);
        assertThat(actualUsers).contains(specialist);
    }

    @Test
    void findClientsWhoDidNotPaid() {
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
        entityManager.persist(client);
        entityManager.persist(specialist);
        entityManager.persist(address);
        entityManager.persist(service);
        appointment.setClient(client);
        appointment.setSpecialist(specialist);
        appointment.setAddress(address);
        appointment.setService(service);
        entityManager.persist(appointment);

        List<Tuple> actualUserAndAmountList = userRepository.findClientsWithAmountWhoDidNotPaid();

        List<User> actualUserList = actualUserAndAmountList.stream()
                .map(it -> it.get(0, User.class))
                .toList();

        assertThat(actualUserAndAmountList).hasSize(1);
        assertThat(actualUserList).contains(client);
    }
}

