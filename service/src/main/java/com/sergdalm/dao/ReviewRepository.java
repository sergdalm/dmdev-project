package com.sergdalm.dao;

import com.sergdalm.entity.Review;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

@Repository
public class ReviewRepository extends RepositoryBase<Review, Integer> {

    public ReviewRepository(SessionFactory sessionFactory) {
        super(Review.class, sessionFactory);
    }
}
