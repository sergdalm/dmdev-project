package com.sergdalm.dao;

import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;
import com.sergdalm.entity.Appointment;
import com.sergdalm.entity.AppointmentStatus;
import com.sergdalm.filter.QPredicate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static com.sergdalm.entity.QAppointment.appointment;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {

    default List<Appointment> findByStatus(List<AppointmentStatus> statuses, EntityManager entityManager) {
        Predicate predicate = QPredicate.builder()
                .add(statuses, appointment.status::in)
                .buildAnd();
        return new JPAQuery<Appointment>(entityManager)
                .select(appointment)
                .from(appointment)
                .where(predicate)
                .fetch();
    }
}
