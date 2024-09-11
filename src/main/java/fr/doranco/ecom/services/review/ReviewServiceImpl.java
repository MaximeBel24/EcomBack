package fr.doranco.ecom.services.review;

import fr.doranco.ecom.dto.ReviewDto;
import fr.doranco.ecom.entities.Product;
import fr.doranco.ecom.entities.Review;
import fr.doranco.ecom.entities.User;
import fr.doranco.ecom.exceptions.ProductException;
import fr.doranco.ecom.exceptions.ReviewException;
import fr.doranco.ecom.exceptions.UserException;
import fr.doranco.ecom.repositories.ProductRepository;
import fr.doranco.ecom.repositories.ReviewRepository;
import fr.doranco.ecom.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    @Override
    public ReviewDto createReview(ReviewDto reviewDto) {
        User user = userRepository.findById(reviewDto.getUserId())
                .orElseThrow(() -> new UserException("Utilisateur introuvable"));

        Product product = productRepository.findById(reviewDto.getProductId())
                .orElseThrow(() -> new ProductException("Produit introuvable"));

        Review review = new Review();
        review.setRating(reviewDto.getRating());
        review.setDescription(reviewDto.getDescription());
        review.setImg(reviewDto.getImg());
        review.setUser(user);
        review.setProduct(product);
        review.setReviewDate(new Date());

        Review savedReview = reviewRepository.save(review);

        return convertToDto(savedReview);
    }

    @Override
    public ReviewDto updateReview(Long id, ReviewDto reviewDto) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new ReviewException("Avis introuvable"));

        review.setRating(reviewDto.getRating());
        review.setDescription(reviewDto.getDescription());
        review.setImg(reviewDto.getImg());
        review.setReviewDate(new Date());

        Review updatedReview = reviewRepository.save(review);
        return convertToDto(updatedReview);
    }

    @Override
    public void deleteReview(Long id) {
        reviewRepository.deleteById(id);
    }

    @Override
    public ReviewDto getReviewById(Long id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new ReviewException("Avis introuvable"));
        return convertToDto(review);
    }

    @Override
    public List<ReviewDto> getReviewsByProductId(Long productId) {
        return reviewRepository.findAllByProductId(productId)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ReviewDto> getReviewsByUserId(Long userId) {
        return reviewRepository.findAllByUserId(userId)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private ReviewDto convertToDto(Review review) {
        return new ReviewDto(
                review.getId(),
                review.getRating(),
                review.getDescription(),
                review.getImg(),
                review.getUser().getId(),
                review.getProduct().getId(),
                review.getReviewDate().toString()
        );
    }
}
