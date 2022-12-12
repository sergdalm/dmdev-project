package com.sergdalm.integration.dao;

import com.querydsl.core.Tuple;
import com.sergdalm.EntityUtil;
import com.sergdalm.dao.UserRepository;
import com.sergdalm.dao.filter.SpecialistFilter;
import com.sergdalm.dao.filter.UserFilter;
import com.sergdalm.entity.Gender;
import com.sergdalm.entity.ServiceName;
import com.sergdalm.entity.User;
import com.sergdalm.integration.IntegrationTestBase;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@RequiredArgsConstructor
class UserRepositoryIT extends IntegrationTestBase {

    private final UserRepository userRepository;

    @ParameterizedTest(name = "{index} {0}")
    @MethodSource("getSpecialistFilterWithExpectedUsrList")

    void findSpecialistsByFilter(String testDisplayName,
                                 SpecialistFilter filter,
                                 List<String> expectedList) {
        Page<User> actualResult = userRepository.findAll(filter, PageRequest.of(0, 6));
        assertThat(actualResult).hasSize(expectedList.size());
        List<String> actualEmailList = actualResult.stream()
                .map(User::getEmail)
                .toList();
        for (String expectedSpecialistEmail : expectedList) {
            assertThat(actualEmailList).contains(expectedSpecialistEmail);
        }
    }

    static Stream<Arguments> getSpecialistFilterWithExpectedUsrList() {
        List<String> listWithSpecialistDmitry = List.of("dmitry@gmail.com");
        return Stream.of(
                // Find specialist who has certain email
                Arguments.of("email",
                        SpecialistFilter.builder()
                                .email("dm")
                                .build(),
                        listWithSpecialistDmitry),
                // Find specialist who has certain first name
                Arguments.of("first name",
                        SpecialistFilter.builder()
                                .firstName("dm")
                                .build(),
                        listWithSpecialistDmitry),
                // Find specialist who has certain last name
                Arguments.of("last name",
                        SpecialistFilter.builder()
                                .lastName("ch")
                                .build(),
                        List.of("dmitry@gmail.com", "svetlana@gmail.com")),
                // Find specialist whose gender is male
                Arguments.of("gender: male",
                        SpecialistFilter.builder()
                                .gender(Gender.MALE)
                                .build(),
                        listWithSpecialistDmitry),
                // Find specialist whose gender is female
                Arguments.of("gender: female",
                        SpecialistFilter.builder()
                                .gender(Gender.FEMALE)
                                .build(),
                        List.of("natali@gmail.com", "alex@gmail.com", "svetlana@gmail.com", "marina@gmail.com", "katya@gmail.com")),
                // Find specialist who are younger than maxAge
                Arguments.of("minimum age",
                        SpecialistFilter.builder()
                                .minAge(LocalDate.now().getYear() - 1980)
                                .build(),
                        List.of("dmitry@gmail.com", "svetlana@gmail.com")),
                // Find specialist who are older than maxAge
                Arguments.of("maximum age",
                        SpecialistFilter.builder()
                                .maxAge(LocalDate.now().getYear() - 1990)
                                .build(),
                        List.of("alex@gmail.com", "marina@gmail.com", "katya@gmail.com")),
                // Find specialist who registered before certain date
                Arguments.of("registered before date",
                        SpecialistFilter.builder()
                                .registeredBeforeDate(LocalDate.of(2022, 11, 15))
                                .build(),
                        List.of("dmitry@gmail.com", "natali@gmail.com", "alex@gmail.com", "svetlana@gmail.com")),
                // Find specialist who registered after certain date
                Arguments.of("registered after date",
                        SpecialistFilter.builder()
                                .registeredAfterDate(LocalDate.of(2022, 11, 15))
                                .build(),
                        List.of("marina@gmail.com", "katya@gmail.com")),
                // Find specialist who have review
                Arguments.of("with reviews",
                        SpecialistFilter.builder()
                                .hasReviews(true)
                                .build(),
                        listWithSpecialistDmitry),
                // Find Specialists by filter with condition on service names
                Arguments.of("by service name",
                        SpecialistFilter.builder()
                                .serviceNames(List.of(ServiceName.HONEY_MASSAGE, ServiceName.CLASSIC_MASSAGE))
                                .build(),
                        listWithSpecialistDmitry),
                // Find specialists who have appointments on certain address's id
                Arguments.of("by appointments' address ",
                        SpecialistFilter.builder()
                                .addressIdWhereHaveAppointments(List.of(2))
                                .build(),
                        listWithSpecialistDmitry),
                // Find specialists who are available on certain dates
                Arguments.of("available on dates",
                        SpecialistFilter.builder()
                                .availableDates(List.of(LocalDate.of(2022, 11, 26)))
                                .build(),
                        listWithSpecialistDmitry),
                // Find specialists who are available in certain
                Arguments.of("available at times",
                        SpecialistFilter.builder()
                                .availableTimes(List.of(LocalTime.of(20, 0, 0)))
                                .build(),
                        List.of("natali@gmail.com")),
                // Find specialists who are available on certain address's id
                Arguments.of("by available addresses",
                        SpecialistFilter.builder()
                                .availableAddressesId(List.of(1))
                                .build(),
                        listWithSpecialistDmitry)
        );
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

    @ParameterizedTest(name = "{index} {0}")
    @MethodSource("getUserFiltersWithUserEmails")
    void findUserByFilter(String testDisplayName,
                          UserFilter userFilter,
                          List<String> emails) {

        List<User> actualResult = userRepository.findAll(userFilter);
        List<String> actualEmails = actualResult.stream().map(User::getEmail).toList();

        assertThat(actualResult).hasSize(emails.size());
        assertEquals(emails, actualEmails);
    }

    static Stream<Arguments> getUserFiltersWithUserEmails() {
        return Stream.of(
                Arguments.of("first name",
                        UserFilter.builder()
                                .firstName("Dmi")
                                .build(),
                        List.of("dmitry@gmail.com")),
                Arguments.of("last name",
                        UserFilter.builder()
                                .lastName("cher")
                                .build(),
                        List.of("dmitry@gmail.com", "svetlana@gmail.com")),
                Arguments.of("birthday after the date",
                        UserFilter.builder()
                                .birthdayAfterDate(LocalDate.of(1990, 11, 1))
                                .build(),
                        List.of("alex@gmail.com", "katya@gmail.com")),
                Arguments.of("birthday before the date",
                        UserFilter.builder()
                                .birthdayBeforeDate(LocalDate.of(1960, 1, 1))
                                .build(),
                        List.of("svetlana@gmail.com")),
                Arguments.of("gender",
                        UserFilter.builder()
                                .gender(Gender.MALE)
                                .build(),
                        List.of("dmitry@gmail.com")),
                Arguments.of("mobile phone",
                        UserFilter.builder()
                                .mobilePhoneNumber("214")
                                .build(),
                        List.of("katya@gmail.com")),
                Arguments.of("have description",
                        UserFilter.builder()
                                .build(),
                        List.of("dmitry@gmail.com", "natali@gmail.com")),
                Arguments.of("registered after date",
                        UserFilter.builder()
                                .registeredAfterDate(LocalDate.of(2022, 11, 14))
                                .build(),
                        List.of("marina@gmail.com", "katya@gmail.com")),
                Arguments.of("registered before date",
                        UserFilter.builder()
                                .registeredAfterDate(LocalDate.of(2022, 11, 15))
                                .build(),
                        List.of("dmitry@gmail.com", "natali@gmail.com", "alex@gmail.com", "svetlana@gmail.com"))
        );
    }
}

