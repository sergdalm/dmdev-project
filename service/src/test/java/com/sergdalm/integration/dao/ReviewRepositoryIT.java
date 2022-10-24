package com.sergdalm.integration.dao;

import com.sergdalm.EntityUtil;
import com.sergdalm.config.BeanProvider;
import com.sergdalm.dao.ReviewRepository;
import com.sergdalm.entity.Review;
import com.sergdalm.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ReviewRepositoryIT {

    private final SessionFactory sessionFactory = BeanProvider.getSessionFactory();
    private final ReviewRepository reviewRepository = BeanProvider.getReviewRepository();

    @Test
    void SaveAndFindById() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        User client = EntityUtil.getUserClient();
        User specialist = EntityUtil.getUserSpecialist();
        session.save(client);
        session.save(specialist);
        Review review = EntityUtil.getReview();
        review.setClient(client);
        review.setSpecialist(specialist);
        reviewRepository.save(review);
        session.flush();
        session.clear();

        Optional<Review> actualOptionalReview = reviewRepository.findById(review.getId());

        assertThat(actualOptionalReview).isPresent();
        assertEquals(review, actualOptionalReview.get());
        assertEquals(review.getClient(), actualOptionalReview.get().getClient());
        assertEquals(review.getSpecialist(), actualOptionalReview.get().getSpecialist());

        session.getTransaction().rollback();
    }

    @Test
    void SaveAndUpdate() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        User client = EntityUtil.getUserClient();
        User specialist = EntityUtil.getUserSpecialist();
        session.save(client);
        session.save(specialist);
        Review review = EntityUtil.getReview();
        review.setClient(client);
        review.setSpecialist(specialist);
        reviewRepository.save(review);
        session.flush();
        session.clear();

        String newContent = "Great!";
        review.setContent(newContent);
        reviewRepository.update(review);
        session.flush();
        session.clear();
        Optional<Review> actualOptionalUpdatedReview = reviewRepository.findById(review.getId());

        assertThat(actualOptionalUpdatedReview).isPresent();
        assertEquals(review, actualOptionalUpdatedReview.get());
        assertEquals(newContent, actualOptionalUpdatedReview.get().getContent());

        session.getTransaction().rollback();
    }

    @Test
    void saveAndDelete() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        User client = EntityUtil.getUserClient();
        User specialist = EntityUtil.getUserSpecialist();
        session.save(client);
        session.save(specialist);
        Review review = EntityUtil.getReview();
        review.setClient(client);
        review.setSpecialist(specialist);
        reviewRepository.save(review);
        session.flush();
        session.clear();
        reviewRepository.delete(review);

        Optional<Review> actualOptionalReview = reviewRepository.findById(review.getId());

        assertThat(actualOptionalReview).isNotPresent();

        session.getTransaction().rollback();
    }

    @Test
    void findAll() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        User client = EntityUtil.getUserClient();
        User specialist = EntityUtil.getUserSpecialist();
        session.save(client);
        session.save(specialist);
        Review review = EntityUtil.getReview();
        review.setClient(client);
        review.setSpecialist(specialist);
        reviewRepository.save(review);
        session.flush();
        session.clear();

        List<Review> actualAllReviews = reviewRepository.findAll();

        assertThat(actualAllReviews).hasSize(1);
        assertThat(actualAllReviews).contains(review);

        session.getTransaction().rollback();
    }
}