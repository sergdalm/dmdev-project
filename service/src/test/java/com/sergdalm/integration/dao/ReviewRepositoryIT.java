package com.sergdalm.integration.dao;

import com.sergdalm.EntityUtil;
import com.sergdalm.dao.ReviewRepository;
import com.sergdalm.entity.Review;
import com.sergdalm.entity.User;
import com.sergdalm.integration.IntegrationTestBase;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@RequiredArgsConstructor
class ReviewRepositoryIT extends IntegrationTestBase {

    private final EntityManager entityManager;
    private final ReviewRepository reviewRepository;

    @Test
    void SaveAndFindById() {
        User client = EntityUtil.getClientSvetlana();
        User specialist = EntityUtil.getSpecialistDmitry();
        entityManager.persist(client);
        entityManager.persist(specialist);
        Review review = EntityUtil.getReview();
        review.setClient(client);
        review.setSpecialist(specialist);
        reviewRepository.save(review);
        entityManager.flush();
        entityManager.clear();

        Optional<Review> actualOptionalReview = reviewRepository.findById(review.getId());

        assertThat(actualOptionalReview).isPresent();
        assertEquals(review, actualOptionalReview.get());
        assertEquals(review.getClient(), actualOptionalReview.get().getClient());
        assertEquals(review.getSpecialist(), actualOptionalReview.get().getSpecialist());
    }

    @Test
    void SaveAndUpdate() {
        User client = EntityUtil.getClientSvetlana();
        User specialist = EntityUtil.getSpecialistDmitry();
        entityManager.persist(client);
        entityManager.persist(specialist);
        Review review = EntityUtil.getReview();
        review.setClient(client);
        review.setSpecialist(specialist);
        reviewRepository.save(review);
        entityManager.flush();
        entityManager.clear();

        String newContent = "Great!";
        review.setContent(newContent);
        reviewRepository.save(review);
        entityManager.flush();
        entityManager.clear();
        Optional<Review> actualOptionalUpdatedReview = reviewRepository.findById(review.getId());

        assertThat(actualOptionalUpdatedReview).isPresent();
        assertEquals(review, actualOptionalUpdatedReview.get());
        assertEquals(newContent, actualOptionalUpdatedReview.get().getContent());
    }

    @Test
    void saveAndDelete() {
        User client = EntityUtil.getClientSvetlana();
        User specialist = EntityUtil.getSpecialistDmitry();
        entityManager.persist(client);
        entityManager.persist(specialist);
        Review review = EntityUtil.getReview();
        review.setClient(client);
        review.setSpecialist(specialist);
        reviewRepository.save(review);
        entityManager.flush();
        entityManager.clear();

        Review savedReview = entityManager.find(Review.class, review.getId());
        reviewRepository.delete(savedReview);
        Optional<Review> actualOptionalReview = reviewRepository.findById(review.getId());

        assertThat(actualOptionalReview).isNotPresent();
    }

    @Test
    void findAll() {
        User client = EntityUtil.getClientSvetlana();
        User specialist = EntityUtil.getSpecialistDmitry();
        entityManager.persist(client);
        entityManager.persist(specialist);
        Review review = EntityUtil.getReview();
        review.setClient(client);
        review.setSpecialist(specialist);
        reviewRepository.save(review);
        entityManager.flush();
        entityManager.clear();

        List<Review> actualAllReviews = reviewRepository.findAll();

        assertThat(actualAllReviews).hasSize(1);
        assertThat(actualAllReviews).contains(review);
    }
}