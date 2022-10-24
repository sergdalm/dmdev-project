package com.sergdalm.integration.entity;

import com.sergdalm.EntityUtil;
import com.sergdalm.config.BeanProvider;
import com.sergdalm.entity.Review;
import com.sergdalm.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class ReviewIT {

    private final SessionFactory sessionFactory = BeanProvider.getSessionFactory();
    private final User specialist = EntityUtil.getUserSpecialist();
    private final User client = EntityUtil.getUserClient();
    private final Review review = EntityUtil.getReview();

    @BeforeEach
    void saveEntities() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        session.persist(specialist);
        session.persist(client);
        review.setClient(client);
        review.setSpecialist(specialist);

        session.persist(review);

        session.getTransaction().commit();
    }

    @Test
    void shouldGetReview() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        Review actualReview = session.get(Review.class, review.getId());

        assertEquals(review, actualReview);
        assertEquals(client, actualReview.getClient());
        assertEquals(specialist, actualReview.getSpecialist());

        session.getTransaction().rollback();
    }

    @Test
    void shouldUpdateReview() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        review.setContent("Great!");
        session.update(review);
        session.flush();
        session.clear();

        Review actualReview = session.get(Review.class, review.getId());

        assertEquals(review, actualReview);

        session.getTransaction().rollback();
    }

    @Test
    void shouldDeleteReview() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        Review savedReview = session.get(Review.class, review.getId());

        session.delete(savedReview);
        session.flush();
        session.clear();

        Review actualReview = session.get(Review.class, review.getId());

        assertNull(actualReview);

        session.getTransaction().rollback();
    }

    @Test
    void shouldDeleteReviewWhenDeletingSpecialist() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        session.delete(specialist);
        session.flush();
        session.clear();

        Review actualReview = session.get(Review.class, review.getId());

        assertNull(actualReview);

        session.getTransaction().rollback();
    }

    @Test
    void shouldDeleteReviewWhenDeletingClient() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        session.delete(client);
        session.flush();
        session.clear();

        Review actualReview = session.get(Review.class, review.getId());

        assertNull(actualReview);

        session.getTransaction().rollback();
    }

    @AfterEach
    void cleanDataBse() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        session.createSQLQuery("delete from review")
                .executeUpdate();
        session.createSQLQuery("delete from users")
                .executeUpdate();

        session.getTransaction().commit();
    }
}
