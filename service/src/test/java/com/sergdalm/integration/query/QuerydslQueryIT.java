package com.sergdalm.integration.query;

import com.querydsl.jpa.impl.JPAQuery;
import com.sergdalm.entity.Appointment;
import com.sergdalm.entity.AppointmentStatus;
import com.sergdalm.entity.SpecialistAvailableTime;
import com.sergdalm.entity.User;
import com.sergdalm.filter.SpecialistAvailableTimeFilter;
import com.sergdalm.query.QuerydslQuery;
import com.sergdalm.util.HibernateTestUtil;
import com.sergdalm.util.TestDataImporter;
import lombok.Cleanup;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.graph.GraphSemantic;
import org.hibernate.graph.RootGraph;
import org.hibernate.graph.SubGraph;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.sergdalm.entity.QAppointment.appointment;
import static com.sergdalm.entity.QUser.user;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

public class QuerydslQueryIT {

    private static final SessionFactory SESSION_FACTORY = HibernateTestUtil.buildSessionFactory();

    private final QuerydslQuery querydslQuery = new QuerydslQuery();

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

        List<SpecialistAvailableTime> result = querydslQuery.getSpecialistAvailableTimeList(session, filter);
        List<LocalTime> times = result.stream().map(SpecialistAvailableTime::getTime).collect(toList());
        assertThat(times).contains(
                LocalTime.of(14, 0),
                LocalTime.of(15, 0)
        );
        assertThat(times).doesNotContain(
                LocalTime.of(12, 0),
                LocalTime.of(13, 0),
                LocalTime.of(16, 0)
        );

        session.getTransaction().commit();
    }

    @Test
    void getUsersWhoCanceledAppointmentsQDSL() {
        @Cleanup Session session = SESSION_FACTORY.openSession();
        session.beginTransaction();

        List<User> users = new JPAQuery<User>(session)
                .select(user)
                .from(user)
                .join(user.clientAppointments, appointment)
                .where(appointment.status.eq(AppointmentStatus.CANCELED))
                .fetch();

        assertThat(users).contains(TestDataImporter.clientSvetlana, TestDataImporter.clientKirill);

        session.getTransaction().commit();
    }

    @Test
    void getAmountEarnedBySpecialist() {
        @Cleanup Session session = SESSION_FACTORY.openSession();
        session.beginTransaction();

        RootGraph<User> userGraph = session.createEntityGraph(User.class);
        userGraph.addAttributeNodes("specialistAppointments");
        SubGraph<Appointment> specialistAppointmentSubGraph = userGraph.addSubgraph(
                "specialistAppointments", Appointment.class
        );
        specialistAppointmentSubGraph.addAttributeNodes("price");
        specialistAppointmentSubGraph.addAttributeNodes("status");

        Map<String, Object> properties = Map.of(
                GraphSemantic.LOAD.getJpaHintName(),
                userGraph
        );

        User specialist = session.find(User.class, TestDataImporter.specialistDmitry.getId(), properties);
        Map<User, Integer> userAmount = new HashMap<>();
        userAmount.put(specialist, specialist.getSpecialistAppointments().
                stream()
                .filter(a -> a.getStatus().equals(AppointmentStatus.COMPLETED_PAID))
                .mapToInt(Appointment::getPrice)
                .sum());

        session.getTransaction().commit();
    }
}
