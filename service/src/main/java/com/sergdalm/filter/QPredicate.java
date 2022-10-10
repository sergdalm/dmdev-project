package com.sergdalm.filter;

import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class QPredicate {

    private final List<Predicate> predicates = new ArrayList<>();

    public static QPredicate builder() {
        return new QPredicate();
    }

    public <T> QPredicate add(T object, Function<T, Predicate> function) {
        if (object != null) {
            predicates.add(function.apply(object));
        }
        return this;
    }

    public <T> QPredicate add(List<T> objectList, Function<List<T>, Predicate> function) {
        if (objectList != null) {
            predicates.add(function.apply(objectList));
        }
        return this;
    }

    public Predicate  buildAnd() {
        return ExpressionUtils.allOf(predicates);
    }

    public Predicate  buildOr() {
        return ExpressionUtils.anyOf(predicates);
    }


}
