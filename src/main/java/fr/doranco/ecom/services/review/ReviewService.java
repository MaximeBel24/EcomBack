package fr.doranco.ecom.services.review;

import fr.doranco.ecom.dto.ReviewDto;

import java.util.List;

public interface ReviewService {
    ReviewDto createReview(ReviewDto reviewDto);
    ReviewDto updateReview(Long id, ReviewDto reviewDto);
    void deleteReview(Long id);
    ReviewDto getReviewById(Long id);
    List<ReviewDto> getReviewsByProductId(Long productId);
    List<ReviewDto> getReviewsByUserId(Long userId);
}
