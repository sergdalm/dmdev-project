package com.sergdalm.dao;


import com.sergdalm.dao.filter.CriteriaPredicate;
import com.sergdalm.dao.filter.UserFilter;
import com.sergdalm.entity.User;
import com.sergdalm.entity.UserInfo;
import com.sergdalm.entity.UserInfo_;
import com.sergdalm.entity.User_;
import lombok.AllArgsConstructor;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static com.sergdalm.entity.User_.email;
import static com.sergdalm.entity.User_.firstName;
import static com.sergdalm.entity.User_.lastName;
import static com.sergdalm.entity.User_.mobilePhoneNumber;

@AllArgsConstructor
public class UserSearchFilterImpl implements UserSearchFilter {

    private final EntityManager entityManager;

    @Override
    public List<User> findAll(UserFilter userFilter) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> criteria = cb.createQuery(User.class);
        Root<User> user = criteria.from(User.class);
        Join<User, UserInfo> userInfo = user.join(User_.userInfo);

        Predicate[] predicates = CriteriaPredicate.builder()
                .addStringWithPercentage(userFilter.getEmail(), obj -> cb.like(cb.lower(user.get(email)), obj))
                .addStringWithPercentage(userFilter.getFirstName(), obj -> cb.like(cb.lower(user.get(firstName)), obj))
                .addStringWithPercentage(userFilter.getLastName(), obj -> cb.like(cb.lower(user.get(lastName)), obj))
                .addStringWithPercentage(userFilter.getMobilePhoneNumber(), obj -> cb.like(user.get(mobilePhoneNumber), obj))
                .add(userFilter.getGender(), obj -> cb.equal(userInfo.get(UserInfo_.gender), obj))
                .add(userFilter.getBirthdayAfterDate(), obj -> cb.greaterThan(userInfo.get(UserInfo_.birthday), obj))
                .add(userFilter.getBirthdayBeforeDate(), obj -> cb.lessThan(userInfo.get(UserInfo_.birthday), obj))
                .add(userFilter.getRegisteredAfterDate(), obj -> cb.greaterThan(userInfo.get(UserInfo_.registeredAt), LocalDateTime.of(obj, LocalTime.MAX)))
                .add(userFilter.getRegisteredBeforeDate(), obj -> cb.lessThan(userInfo.get(UserInfo_.registeredAt), LocalDateTime.of(obj, LocalTime.MIN)))
                .build();

        criteria.where(predicates);

        return entityManager.createQuery(criteria)
                .getResultList();
    }
}
