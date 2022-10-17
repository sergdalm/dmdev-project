package com.sergdalm.dao;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQuery;
import com.sergdalm.entity.AppointmentStatus;
import com.sergdalm.entity.Service;
import com.sergdalm.entity.ServiceName;
import com.sergdalm.entity.Service_;
import com.sergdalm.entity.SpecialistService;
import com.sergdalm.entity.SpecialistService_;
import com.sergdalm.entity.User;
import com.sergdalm.entity.User_;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.ListJoin;
import javax.persistence.criteria.Root;
import java.util.List;

import static com.sergdalm.entity.QAppointment.appointment;
import static com.sergdalm.entity.QUser.user;

public class UserRepository extends RepositoryBase<User, Integer> {

    private SessionFactory sessionFactory;

    public UserRepository(SessionFactory sessionFactory) {
        super(User.class, sessionFactory);
        this.sessionFactory = sessionFactory;
    }


    public List<User> getUsersByMassageType(List<ServiceName> serviceNames) {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<User> criteria = cb.createQuery(User.class);
        Root<User> user = criteria.from(User.class);
        ListJoin<User, SpecialistService> specialistService = user.join(User_.specialistServices);
        Join<SpecialistService, Service> service = specialistService.join(SpecialistService_.service);

        criteria.select(user)
                .where(service.get(Service_.name)
                        .in(serviceNames))
                .groupBy(user.get(User_.id));

        return session.createQuery(criteria)
                .list();
    }

    public List<Tuple> findClientsWithAmountWhoDidNotPaid() {
        Session session = sessionFactory.getCurrentSession();
        return new JPAQuery<Tuple>(session)
                .select(user, appointment.price.sum())
                .from(user)
                .join(user.clientAppointments, appointment)
                .where(appointment.status.eq(AppointmentStatus.COMPLETED_NOT_PAID))
                .groupBy(user.id)
                .fetch();
    }
}
