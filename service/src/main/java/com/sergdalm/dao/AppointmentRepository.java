package com.sergdalm.dao;

import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;
import com.sergdalm.entity.Appointment;
import com.sergdalm.entity.AppointmentStatus;
import com.sergdalm.filter.QPredicate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

import static com.sergdalm.entity.QAppointment.appointment;

public class AppointmentRepository extends RepositoryBase<Appointment, Integer> {

    private final SessionFactory sessionFactory;

    public AppointmentRepository(SessionFactory sessionFactory) {
        super(Appointment.class, sessionFactory);
        this.sessionFactory = sessionFactory;
    }

    public List<Appointment> findByStatus(List<AppointmentStatus> statuses) {
        Session session = sessionFactory.getCurrentSession();
        Predicate predicate = QPredicate.builder()
                .add(statuses, appointment.status::in)
                .buildAnd();
        return new JPAQuery<Appointment>(session)
                .select(appointment)
                .from(appointment)
                .where(predicate)
                .fetch();
    }
}
