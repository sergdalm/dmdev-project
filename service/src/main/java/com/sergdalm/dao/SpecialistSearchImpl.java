package com.sergdalm.dao;

import com.sergdalm.dao.filter.CriteriaPredicate;
import com.sergdalm.dao.filter.SpecialistFilter;
import com.sergdalm.entity.Address;
import com.sergdalm.entity.Address_;
import com.sergdalm.entity.Appointment;
import com.sergdalm.entity.Appointment_;
import com.sergdalm.entity.Review;
import com.sergdalm.entity.Review_;
import com.sergdalm.entity.Service;
import com.sergdalm.entity.Service_;
import com.sergdalm.entity.SpecialistAvailableTime;
import com.sergdalm.entity.SpecialistAvailableTime_;
import com.sergdalm.entity.SpecialistService;
import com.sergdalm.entity.SpecialistService_;
import com.sergdalm.entity.User;
import com.sergdalm.entity.UserInfo;
import com.sergdalm.entity.UserInfo_;
import com.sergdalm.entity.User_;
import lombok.AllArgsConstructor;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.ListJoin;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static com.sergdalm.entity.User_.email;
import static com.sergdalm.entity.User_.firstName;
import static com.sergdalm.entity.User_.id;
import static com.sergdalm.entity.User_.lastName;
import static com.sergdalm.entity.User_.mobilePhoneNumber;
import static com.sergdalm.entity.User_.specialistServices;

@AllArgsConstructor
public class SpecialistSearchImpl implements SpecialistSearch {

    private final EntityManager entityManager;

    public List<User> findSpecialistsByFilter(SpecialistFilter specialistFilter) {

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> criteria = cb.createQuery(User.class);
        Root<User> user = criteria.from(User.class);
        Join<User, UserInfo> userInfo = user.join(User_.userInfo);

        Subquery<Long> serviceSubquery = criteria.subquery(Long.class);
        Root<User> serviceSubqueryUser = serviceSubquery.correlate(user);
        ListJoin<User, SpecialistService> ssJoin = serviceSubqueryUser.join(specialistServices);
        Join<SpecialistService, Service> serviceJoin = ssJoin.join(SpecialistService_.service);

        Subquery<Long> availableTimeSubquery = criteria.subquery(Long.class);
        Root<User> availableTimeSubqueryUser = availableTimeSubquery.correlate(user);
        ListJoin<User, SpecialistAvailableTime> availableTimeJoin = availableTimeSubqueryUser.join(User_.specialistAvailableTimes);

        Subquery<Long> reviewSubquery = criteria.subquery(Long.class);
        Root<Review> subReview = reviewSubquery.from(Review.class);
        Join<Review, User> reviewSubUser = subReview.join(Review_.specialist);

        Subquery<Long> appointmentSubquery = criteria.subquery(Long.class);
        Root<User> appointmentSubqueryUser = appointmentSubquery.correlate(user);
        ListJoin<User, Appointment> appointmentUserJoin = appointmentSubqueryUser.join(User_.specialistAppointments);
        Join<Appointment, Address> appointmentAddressJoin = appointmentUserJoin.join(Appointment_.address);

        Subquery<Long> availableTimeAddressSubquerry = criteria.subquery(Long.class);
        Root<User> availableTimeAddressSubquerryUser = availableTimeAddressSubquerry.correlate(user);
        ListJoin<User, SpecialistAvailableTime> atJoin = availableTimeAddressSubquerryUser.join(User_.specialistAvailableTimes);
        Join<SpecialistAvailableTime, Address> availableTimeAddressJoin = atJoin.join(SpecialistAvailableTime_.address);

        Subquery<Long> availableTimeSubquerry = criteria.subquery(Long.class);
        Root<SpecialistAvailableTime> subAvailableTime = availableTimeSubquerry.from(SpecialistAvailableTime.class);
        Join<SpecialistAvailableTime, User> availableTimeSubUser = subAvailableTime.join(SpecialistAvailableTime_.specialist);

        Predicate[] predicates = CriteriaPredicate.builder()
                // Find specialists who have this email
                .addStringWithPercentage(specialistFilter.getEmail(), obj -> cb.like(cb.lower(user.get(email)), obj))
                // Find specialists who have this first name
                .addStringWithPercentage(specialistFilter.getFirstName(), obj -> cb.like(cb.lower(user.get(firstName)), obj))
                // Find specialists who have this last name
                .addStringWithPercentage(specialistFilter.getLastName(), obj -> cb.like(cb.lower(user.get(lastName)), obj))
                // Find specialists who have this mobile phone
                .addStringWithPercentage(specialistFilter.getMobilePhoneNumber(), obj -> cb.like(user.get(mobilePhoneNumber), obj))
                // Find specialists who have this gender
                .add(specialistFilter.getGender(), obj -> cb.equal(userInfo.get(UserInfo_.gender), obj))
                // Find specialists who have birthday before this date
                .add(specialistFilter.getBirthdayBeforeDate(), obj -> cb.lessThan(userInfo.get(UserInfo_.birthday), obj))
                // Find specialists who have birthday after this date
                .add(specialistFilter.getBirthdayAfterDate(), obj -> cb.greaterThan(userInfo.get(UserInfo_.birthday), obj))
                // Find specialists who were registered before this date
                .add(specialistFilter.getRegisteredBeforeDate(), obj -> cb.lessThan(userInfo.get(UserInfo_.registeredAt), LocalDateTime.of(obj, LocalTime.MIN)))
                // Find specialists who have registered after this date
                .add(specialistFilter.getRegisteredAfterDate(), obj -> cb.greaterThan(userInfo.get(UserInfo_.registeredAt), LocalDateTime.of(obj, LocalTime.MIN)))
                // Find specialists who have reviews
                .add(specialistFilter.getHasReviews(), obj -> {
                    Subquery<Long> sub = reviewSubquery.select(cb.count(subReview.get(Review_.id)))
                            .where(cb.equal(user.get(id), reviewSubUser.get(id)));
                    return cb.greaterThan(sub, 0L);
                })
                // Find specialist who can do particular types of massage
                .add(specialistFilter.getServiceNames(), serviceNames -> cb.ge(serviceSubquery.select(cb.count(serviceJoin.get(Service_.name)))
                        .where(serviceJoin.get(Service_.name).in(serviceNames)), serviceNames.size()))
                // Find specialist who have appointments at these addresses
                .add(specialistFilter.getAddressIdWhereHaveAppointments(), addressIds -> cb.ge(appointmentSubquery.select(cb.count(appointmentAddressJoin.get(Address_.addressName)))
                        .where(appointmentAddressJoin.get(Address_.id).in(addressIds)), addressIds.size()))
                //  Find specialist who have available time in these days
                .add(specialistFilter.getAvailableDates(), dates -> cb.ge(availableTimeSubquery.select(cb.count(availableTimeJoin.get(SpecialistAvailableTime_.date)))
                        .where(availableTimeJoin.get(SpecialistAvailableTime_.date).in(dates)), dates.size())
                )
                //  Find specialist who have available time in these hours
                .add(specialistFilter.getAvailableTimes(), times -> cb.ge(availableTimeSubquery.select(cb.count(availableTimeJoin.get(SpecialistAvailableTime_.time)))
                        .where(availableTimeJoin.get(SpecialistAvailableTime_.time).in(times)), times.size())
                )
                //  Find specialist who have available time at these addresses
                .add(specialistFilter.getAvailableAddressesId(), addressIds -> cb.ge(availableTimeAddressSubquerry.select(cb.count(availableTimeAddressJoin.get(Address_.addressName)))
                        .where(availableTimeAddressJoin.get(Address_.id).in(addressIds)), addressIds.size()))
                //  Find specialist who provides services with price grater than minPrice
                .build();

        criteria.where(predicates)
                .groupBy(user.get(id))
                .getRestriction();
        return entityManager.createQuery(criteria)
                .getResultList();
    }
}
