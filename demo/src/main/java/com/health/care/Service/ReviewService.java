package com.health.care.Service;

import com.health.care.Entity.Review;
import com.health.care.Repository.ReviewRepository;
import jakarta.persistence.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;

    public List<Review> getRecentReviews() {
        return reviewRepository.findTop10ByOrderByDateDesc();
    }

    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    public Page<Review> getReviewsWithPagination(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return reviewRepository.findAllByOrderByDateDesc(pageable);
    }

    public List<Review> getReviewsByService(String service) {
        return reviewRepository.findByServiceOrderByDateDesc(service);
    }

    public List<Review> getReviewsByRating(Integer rating) {
        return reviewRepository.findByRatingOrderByDateDesc(rating);
    }

    public List<Review> getVerifiedReviews() {
        return reviewRepository.findByVerifiedTrueOrderByDateDesc();
    }

    public Optional<Review> getReviewById(String id) {
        return reviewRepository.findById(id);
    }

    public Review saveReview(Review review) {
        return reviewRepository.save(review);
    }

    public void deleteReview(String id) {
        reviewRepository.deleteById(id);
    }

    public double getAverageRating() {
        List<Review> reviews = reviewRepository.findAll();
        return reviews.stream()
                .mapToInt(Review::getRating)
                .average()
                .orElse(0.0);
    }

    public long getTotalReviewsCount() {
        return reviewRepository.count();
    }
}
