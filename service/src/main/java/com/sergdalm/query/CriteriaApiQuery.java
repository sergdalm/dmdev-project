package com.sergdalm.query;

import com.sergdalm.entity.Address_;
import com.sergdalm.entity.AppointmentStatus;
import com.sergdalm.entity.Appointment_;
import com.sergdalm.entity.SpecialistAvailableTime;
import com.sergdalm.entity.SpecialistAvailableTime_;
import com.sergdalm.entity.User;
import com.sergdalm.entity.User_;
import com.sergdalm.filter.SpecialistAvailableTimeFilter;
import org.hibernate.Session;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class CriteriaApiQuery {

    public List<SpecialistAvailableTime> getSpecialistAvailableTimeListByFilterCriteriaApi(Session session,
                                                                                           SpecialistAvailableTimeFilter filter) {
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<SpecialistAvailableTime> criteria = cb.createQuery(SpecialistAvailableTime.class);

        Root<SpecialistAvailableTime> availableTime = criteria.from(SpecialistAvailableTime.class);
        Join<Object, Object> specialist = availableTime.join(SpecialistAvailableTime_.SPECIALIST);
        Join<Object, Object> address = availableTime.join(SpecialistAvailableTime_.ADDRESS);

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(cb.equal(specialist.get(User_.ID), filter.getSpecialistId()));
        predicates.add(cb.equal(address.get(Address_.ID), filter.getAddressId()));
        predicates.add(availableTime.get(SpecialistAvailableTime_.TIME).in(filter.getTimes()));
        predicates.add(availableTime.get(SpecialistAvailableTime_.DATE).in(filter.getDates()));

        criteria.select(availableTime)
                .where(predicates.toArray(Predicate[]::new));

        return session.createQuery(criteria)
                .list();
    }

    public List<User> getUsersWhoCanceledAppointments(Session session) {
        CriteriaBuilder cb = session.getCriteriaBuilder();

        CriteriaQuery<User> criteria = cb.createQuery(User.class);
        Root<User> user = criteria.from(User.class);
        Join<Object, Object> appointment = user.join(User_.CLIENT_APPOINTMENTS);

        criteria.select(user).where(
                cb.equal(appointment.get(Appointment_.STATUS), AppointmentStatus.CANCELED)
        );
        return session.createQuery(criteria)
                .list();
    }
}
