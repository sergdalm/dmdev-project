package com.sergdalm.dao;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;
import com.sergdalm.entity.SpecialistAvailableTime;
import com.sergdalm.filter.QPredicate;
import com.sergdalm.filter.SpecialistAvailableTimeFilter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static com.sergdalm.entity.QSpecialistAvailableTime.specialistAvailableTime;
import static com.sergdalm.entity.QUser.user;

@Repository
public interface SpecialistAvailableTimeRepository extends JpaRepository<SpecialistAvailableTime, Integer> {

    default List<SpecialistAvailableTime> findByFilter(SpecialistAvailableTimeFilter filter, EntityManager entityManager) {
        Predicate predicate = QPredicate.builder()
                .add(filter.getSpecialistId(), user.id::eq)
                .add(filter.getAddressId(), specialistAvailableTime.address.id::eq)
                .add(filter.getDates(), specialistAvailableTime.dateAndTime.date::in)
                .add(filter.getTimes(), specialistAvailableTime.dateAndTime.time::in)
                .buildAnd();

        return new JPAQuery<Tuple>(entityManager)
                .select(specialistAvailableTime)
                .from(user)
                .join(user.specialistAvailableTimes, specialistAvailableTime)
                .orderBy(specialistAvailableTime.dateAndTime.date.asc(),
                        specialistAvailableTime.dateAndTime.time.asc())
                .where(predicate)
                .fetch();
    }
}
