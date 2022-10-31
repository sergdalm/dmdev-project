package com.sergdalm.dao;

import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;
import com.sergdalm.entity.Appointment;
import com.sergdalm.entity.AppointmentStatus;
import com.sergdalm.filter.QPredicate;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static com.sergdalm.entity.QAppointment.appointment;

@Repository
public class AppointmentRepository extends RepositoryBase<Appointment, Integer> {

    public AppointmentRepository(EntityManager entityManager) {
        super(Appointment.class, entityManager);
    }

    public List<Appointment> findByStatus(List<AppointmentStatus> statuses) {
        Predicate predicate = QPredicate.builder()
                .add(statuses, appointment.status::in)
                .buildAnd();
        return new JPAQuery<Appointment>(super.getEntityManager())
                .select(appointment)
                .from(appointment)
                .where(predicate)
                .fetch();
    }
}
