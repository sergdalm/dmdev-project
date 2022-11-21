package com.sergdalm.dao;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQuery;
import com.sergdalm.entity.AppointmentStatus;
import lombok.AllArgsConstructor;

import javax.persistence.EntityManager;
import java.util.List;

import static com.sergdalm.entity.QAppointment.appointment;
import static com.sergdalm.entity.QUser.user;

@AllArgsConstructor
public class ClientSearchImpl implements ClientSearch {

    private final EntityManager entityManager;

    @Override
    public List<Tuple> findClientsWithAmountWhoDidNotPaid() {
        return new JPAQuery<Tuple>(entityManager)
                .select(user, appointment.price.sum())
                .from(user)
                .join(user.clientAppointments, appointment)
                .where(appointment.status.eq(AppointmentStatus.COMPLETED_NOT_PAID))
                .groupBy(user.id)
                .fetch();
    }
}
