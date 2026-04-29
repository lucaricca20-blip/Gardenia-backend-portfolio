package com.betacom.pr.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.betacom.pr.models.Review;

import java.util.List;

public interface IReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByProductId(Long productId);
}