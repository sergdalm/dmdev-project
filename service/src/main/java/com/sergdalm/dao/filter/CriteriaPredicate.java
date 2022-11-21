package com.sergdalm.dao.filter;

import org.apache.commons.collections4.CollectionUtils;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

public class CriteriaPredicate {

    private final List<Predicate> predicates = new ArrayList<>();

    public static CriteriaPredicate builder() {
        return new CriteriaPredicate();
    }

    public <T> CriteriaPredicate add(T object, Function<T, Predicate> function) {
        if (object != null) {
            predicates.add(function.apply(object));
        }
        return this;
    }

    public <T> CriteriaPredicate add(Collection<T> collection, Function<Collection<T>, Predicate> function) {
        if (CollectionUtils.isNotEmpty(collection)) {
            predicates.add(function.apply(collection));
        }
        return this;
    }

    public <T> CriteriaPredicate addAsSingleConditions(Collection<T> collection, Function<Collection<T>, Predicate> function) {
        if (CollectionUtils.isNotEmpty(collection)) {
            for (T condition : collection) {
                predicates.add(function.apply(List.of(condition)));
            }
        }
        return this;
    }

    public <T> CriteriaPredicate addAsSingleConditionsForCollection(Collection<T> collection, Function<T, Predicate> function) {
        if (CollectionUtils.isNotEmpty(collection)) {
            for (T condition : collection) {
                predicates.add(function.apply(condition));
            }
        }
        return this;
    }

    public Predicate[] build() {
        return predicates.toArray(Predicate[]::new);
    }
}
