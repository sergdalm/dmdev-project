package com.sergdalm.dao;


import com.sergdalm.dao.filter.CriteriaPredicate;
import com.sergdalm.dao.filter.UserFilter;
import com.sergdalm.dto.UserWithInfoDto;
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

@AllArgsConstructor
public class UserSearchFilterImpl implements UserSearchFilter {

    private final EntityManager entityManager;

    @Override
    public List<UserWithInfoDto> findAll(UserFilter userFilter) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<UserWithInfoDto> criteria = cb.createQuery(UserWithInfoDto.class);
        Root<User> user = criteria.from(User.class);
        Join<User, UserInfo> userInfo = user.join(User_.userInfo);

        Predicate[] predicates = CriteriaPredicate.builder()
                .add(userFilter.getEmail(), obj -> cb.equal(user.get(User_.email), obj))
                .add(userFilter.getGender(), obj -> cb.equal(userInfo.get(UserInfo_.gender), obj))
                .add(userFilter.getMobilePhoneNumber(), obj -> cb.equal(user.get(User_.mobilePhoneNumber), obj))
                .add(userFilter.getFirstName(), obj -> cb.like(userInfo.get(UserInfo_.firstName), obj))
                .add(userFilter.getLastName(), obj -> cb.like(userInfo.get(UserInfo_.lastName), obj))
                .add(userFilter.getBirthdayAfterDate(), obj -> cb.greaterThan(userInfo.get(UserInfo_.birthday), obj))
                .add(userFilter.getBirthdayBeforeDate(), obj -> cb.lessThan(userInfo.get(UserInfo_.birthday), obj))
                .add(userFilter.getRegisteredAfterDate(), obj -> cb.greaterThan(userInfo.get(UserInfo_.registeredAt), LocalDateTime.of(obj, LocalTime.MAX)))
                .add(userFilter.getRegisteredBeforeDate(), obj -> cb.lessThan(userInfo.get(UserInfo_.registeredAt), LocalDateTime.of(obj, LocalTime.MIN)))
                .add(userFilter.isHasDescription(), obj -> cb.isNotNull(userInfo.get(UserInfo_.description)))
                .build();

        criteria.select(cb.construct(UserWithInfoDto.class,
                user.get(User_.id),
                user.get(User_.email),
                user.get(User_.role),
                user.get(User_.mobilePhoneNumber),
                userInfo.get(UserInfo_.firstName),
                userInfo.get(UserInfo_.lastName),
                userInfo.get(UserInfo_.gender),
                userInfo.get(UserInfo_.birthday),
                userInfo.get(UserInfo_.registeredAt),
                userInfo.get(UserInfo_.description)
        )).where(predicates);

        return entityManager.createQuery(criteria)
                .getResultList();
    }
}
