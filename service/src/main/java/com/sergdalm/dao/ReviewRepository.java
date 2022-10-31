package com.sergdalm.dao;

import com.sergdalm.entity.Review;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class ReviewRepository extends RepositoryBase<Review, Integer> {

    public ReviewRepository(EntityManager entityManager) {
        super(Review.class, entityManager);
    }
}
