package com.sergdalm.integration.query;

import com.sergdalm.entity.SpecialistAvailableTime;
import com.sergdalm.entity.User;
import com.sergdalm.filter.SpecialistAvailableTimeFilter;
import com.sergdalm.query.CriteriaApiQuery;
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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class CriteriaApiQueryIT {

    private static final SessionFactory SESSION_FACTORY = HibernateTestUtil.buildSessionFactory();

    private final CriteriaApiQuery criteriaApiQuery = new CriteriaApiQuery();


    @BeforeAll
    public static void initDb() {
        TestDataImporter.importData(SESSION_FACTORY);
    }

    @AfterAll
    public static void finish() {
        SESSION_FACTORY.close();
    }

    @Test
    void getSpecialistAvailableTimeListByFilter() {
        @Cleanup Session session = SESSION_FACTORY.openSession();
        session.beginTransaction();

        SpecialistAvailableTimeFilter filter = SpecialistAvailableTimeFilter.builder()
                .specialistId(TestDataImporter.specialistDmitry.getId())
                .addressId(TestDataImporter.addressStachek.getId())
                .times(List.of(
                        LocalTime.of(14, 0),
                        LocalTime.of(15, 0)
                ))
                .dates(List.of(
                        LocalDate.of(2022, 10, 16),
                        LocalDate.of(2022, 10, 17)
                ))
                .build();

        List<SpecialistAvailableTime> result = criteriaApiQuery
                .getSpecialistAvailableTimeListByFilterCriteriaApi(session, filter);

        List<LocalTime> resultTimes = result.stream().map(SpecialistAvailableTime::getTime).toList();

        assertThat(resultTimes).contains(
                LocalTime.of(14, 0),
                LocalTime.of(15, 0)
        );
        assertThat(resultTimes).doesNotContain(
                LocalTime.of(12, 0),
                LocalTime.of(13, 0),
                LocalTime.of(16, 0)
        );

        session.getTransaction().commit();
    }

    @Test
    void getUsersWhoCanceledAppointments() {
        @Cleanup Session session = SESSION_FACTORY.openSession();
        session.beginTransaction();

        List<User> users = criteriaApiQuery.getUsersWhoCanceledAppointments(session);

        assertThat(users).contains(TestDataImporter.clientSvetlana, TestDataImporter.clientKirill);

        session.getTransaction().commit();
    }
}
