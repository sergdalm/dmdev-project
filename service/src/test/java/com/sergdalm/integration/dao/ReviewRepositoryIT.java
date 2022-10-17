package com.sergdalm.integration.dao;

import com.sergdalm.EntityUtil;
import com.sergdalm.dao.ReviewRepository;
import com.sergdalm.entity.Review;
import com.sergdalm.entity.User;
import com.sergdalm.util.HibernateTestUtil;
import com.sergdalm.util.TestDataImporter;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ReviewRepositoryIT {

    private static final SessionFactory SESSION_FACTORY = HibernateTestUtil.buildSessionFactory();
    private static final User SPECIALIST = EntityUtil.getUserSpecialist();
    private static final User CLIENT = EntityUtil.getUserClient();
    private final ReviewRepository reviewRepository = new ReviewRepository(SESSION_FACTORY);

    @BeforeAll
    public static void initDb() {
        TestDataImporter.importData(SESSION_FACTORY);

        Session session = SESSION_FACTORY.getCurrentSession();
        session.beginTransaction();

        session.save(SPECIALIST);
        session.save(CLIENT);

        session.getTransaction().commit();
    }

    @AfterAll
    public static void finish() {
        SESSION_FACTORY.close();
    }

    @Test
    void SaveAndFindById() {
        Session session = SESSION_FACTORY.getCurrentSession();
        session.beginTransaction();

        Review review = EntityUtil.getReview();
        review.setClient(CLIENT);
        review.setSpecialist(SPECIALIST);
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
        Session session = SESSION_FACTORY.getCurrentSession();
        session.beginTransaction();

        Review review = EntityUtil.getReview();
        review.setClient(CLIENT);
        review.setSpecialist(SPECIALIST);
        reviewRepository.save(review);
        session.flush();
        session.clear();
        review.setContent("Great!");
        reviewRepository.update(review);
        session.flush();
        session.clear();

        Optional<Review> actualOptionalUpdatedReview = reviewRepository.findById(review.getId());


        assertThat(actualOptionalUpdatedReview).isPresent();
        assertEquals(review, actualOptionalUpdatedReview.get());

        session.getTransaction().rollback();
    }

    @Test
    void saveAndDelete() {
        Session session = SESSION_FACTORY.getCurrentSession();
        session.beginTransaction();

        Review review = EntityUtil.getReview();
        review.setClient(CLIENT);
        review.setSpecialist(SPECIALIST);
        review.setClient(CLIENT);
        review.setSpecialist(SPECIALIST);
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
        Session session = SESSION_FACTORY.getCurrentSession();
        session.beginTransaction();

        List<Review> actualAllReviews = reviewRepository.findAll();
        List<String> actualAllReviewContentList = actualAllReviews.stream()
                .map(Review::getContent)
                .toList();

        assertThat(actualAllReviewContentList).hasSize(2);
        assertThat(actualAllReviewContentList).contains("Dmitry is ver good specialist", "Good");

        session.getTransaction().rollback();
    }
}