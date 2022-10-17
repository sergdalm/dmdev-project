package com.sergdalm.dao;

import com.sergdalm.entity.Review;
import org.hibernate.SessionFactory;

public class ReviewRepository extends RepositoryBase<Review, Integer> {

    private final SessionFactory sessionFactory;

    public ReviewRepository(SessionFactory sessionFactory) {
        super(Review.class, sessionFactory);
        this.sessionFactory = sessionFactory;
    }
}
