package com.sergdalm.integration.dao;

import com.querydsl.core.Tuple;
import com.sergdalm.EntityUtil;
import com.sergdalm.dao.UserRepository;
import com.sergdalm.dao.filter.SpecialistFilter;
import com.sergdalm.dao.filter.UserFilter;
import com.sergdalm.dto.UserWithInfoDto;
import com.sergdalm.entity.Gender;
import com.sergdalm.entity.ServiceName;
import com.sergdalm.entity.User;
import com.sergdalm.integration.IntegrationTestBase;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@RequiredArgsConstructor
class UserRepositoryIT extends IntegrationTestBase {

    private final UserRepository userRepository;

    static Stream<Arguments> getUserFiltersWithUserEmails() {
        return Stream.of(
                Arguments.of(UserFilter.builder()
                                .firstName("Dmi")
                                .build(),
                        List.of("dmitry@gmail.com")),
                Arguments.of(UserFilter.builder()
                                .birthdayAfterDate(LocalDate.of(1990, 11, 1))
                                .build(),
                        List.of("alex@gmail.com", "katya@gmail.com")),
                Arguments.of(UserFilter.builder()
                                .birthdayAfterDate(LocalDate.of(1990, 11, 1))
                                .build(),
                        List.of("alex@gmail.com", "katya@gmail.com")),
                Arguments.of(UserFilter.builder()
                                .gender(Gender.MALE)
                                .build(),
                        List.of("dmitry@gmail.com")),
                Arguments.of(UserFilter.builder()
                                .lastName("cher")
                                .build(),
                        List.of("dmitry@gmail.com", "svetlana@gmail.com")),
                Arguments.of(UserFilter.builder()
                                .birthdayBeforeDate(LocalDate.of(1960, 1, 1))
                                .build(),
                        List.of("svetlana@gmail.com")),
                Arguments.of(UserFilter.builder()
                                .mobilePhoneNumber("214")
                                .build(),
                        List.of("katya@gmail.com")),
                Arguments.of(UserFilter.builder()
                                .hasDescription(true)
                                .build(),
                        List.of("dmitry@gmail.com", "natali@gmail.com")),
                Arguments.of(UserFilter.builder()
                                .registeredAfterDate(LocalDate.of(2022, 11, 14))
                                .build(),
                        List.of("marina@gmail.com", "katya@gmail.com"))
        );
    }

    @Test
    void findSpecialistsByFilterWithConditionOnServiceNames() {
        List<ServiceName> services = List.of(ServiceName.HONEY_MASSAGE, ServiceName.CLASSIC_MASSAGE);
        SpecialistFilter filer = SpecialistFilter.builder()
                .serviceNames(services)
                .build();

        List<User> actualUsers = userRepository.findSpecialistsByFilter(filer);

        assertThat(actualUsers).hasSize(1);
        assertThat(actualUsers.stream().map(User::getEmail).toList()).contains("dmitry@gmail.com");
        System.out.println(actualUsers);
    }

    @Test
    void findSpecialistsByFilterWhoHasReview() {
        SpecialistFilter filer = SpecialistFilter.builder()
                .hasReviews(true)
                .build();

        List<User> actualUsers = userRepository.findSpecialistsByFilter(filer);

        assertThat(actualUsers).hasSize(1);
        assertThat(actualUsers.stream().map(User::getEmail).toList()).contains("dmitry@gmail.com");
        System.out.println(actualUsers);
    }

    @Test
    void findClientsWhoDidNotPaid() {

        User client = EntityUtil.getClientSvetlana();

        List<Tuple> actualUserAndAmountList = userRepository.findClientsWithAmountWhoDidNotPaid();

        List<User> actualUserList = actualUserAndAmountList.stream()
                .map(it -> it.get(0, User.class))
                .toList();

        assertThat(actualUserAndAmountList).hasSize(1);
        assertThat(actualUserList).contains(client);
    }

    @ParameterizedTest
    @MethodSource("getUserFiltersWithUserEmails")
    void findUserByFilter(UserFilter userFilter, List<String> emails) {

        List<UserWithInfoDto> actualResult = userRepository.findAll(userFilter);
        List<String> actualEmails = actualResult.stream().map(UserWithInfoDto::getEmail).toList();

        assertThat(actualResult).hasSize(emails.size());
        assertEquals(emails, actualEmails);
    }
}

