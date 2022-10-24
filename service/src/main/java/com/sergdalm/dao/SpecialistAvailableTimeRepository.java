package com.sergdalm.dao;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;
import com.sergdalm.entity.SpecialistAvailableTime;
import com.sergdalm.filter.QPredicate;
import com.sergdalm.filter.SpecialistAvailableTimeFilter;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.sergdalm.entity.QSpecialistAvailableTime.specialistAvailableTime;
import static com.sergdalm.entity.QUser.user;

@Repository
public class SpecialistAvailableTimeRepository extends RepositoryBase<SpecialistAvailableTime, Integer> {

    public SpecialistAvailableTimeRepository(SessionFactory sessionFactory) {
        super(SpecialistAvailableTime.class, sessionFactory);
    }

    public List<SpecialistAvailableTime> findByFilter(SpecialistAvailableTimeFilter filter) {
        Session session = super.getSessionFactory().getCurrentSession();
        Predicate predicate = QPredicate.builder()
                .add(filter.getSpecialistId(), user.id::eq)
                .add(filter.getAddressId(), specialistAvailableTime.address.id::eq)
                .add(filter.getDates(), specialistAvailableTime.dateAndTime.date::in)
                .add(filter.getTimes(), specialistAvailableTime.dateAndTime.time::in)
                .buildAnd();

        return new JPAQuery<Tuple>(session)
                .select(specialistAvailableTime)
                .from(user)
                .join(user.specialistAvailableTimes, specialistAvailableTime)
                .orderBy(specialistAvailableTime.dateAndTime.date.asc(),
                        specialistAvailableTime.dateAndTime.time.asc())
                .where(predicate)
                .fetch();
    }
}
