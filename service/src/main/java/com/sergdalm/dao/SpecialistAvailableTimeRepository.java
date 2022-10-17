package com.sergdalm.dao;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;
import com.sergdalm.entity.SpecialistAvailableTime;
import com.sergdalm.filter.QPredicate;
import com.sergdalm.filter.SpecialistAvailableTimeFilter;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

import static com.sergdalm.entity.QSpecialistAvailableTime.specialistAvailableTime;
import static com.sergdalm.entity.QUser.user;

public class SpecialistAvailableTimeRepository extends RepositoryBase<SpecialistAvailableTime, Integer> {

    private final SessionFactory sessionFactory;

    public SpecialistAvailableTimeRepository(SessionFactory sessionFactory) {
        super(SpecialistAvailableTime.class, sessionFactory);
        this.sessionFactory = sessionFactory;
    }

    public List<SpecialistAvailableTime> findByFilter(SpecialistAvailableTimeFilter filter) {
        Session session = sessionFactory.getCurrentSession();
        Predicate predicate = QPredicate.builder()
                .add(filter.getSpecialistId(), user.id::eq)
                .add(filter.getAddressId(), specialistAvailableTime.address.id::eq)
                .add(filter.getDates(), specialistAvailableTime.date::in)
                .add(filter.getTimes(), specialistAvailableTime.time::in)
                .buildAnd();

        return new JPAQuery<Tuple>(session)
                .select(specialistAvailableTime)
                .from(user)
                .join(user.specialistAvailableTimes, specialistAvailableTime)
                .orderBy(specialistAvailableTime.date.asc(), specialistAvailableTime.time.asc())
                .where(predicate)
                .fetch();
    }
}
