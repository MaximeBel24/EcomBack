package fr.doranco.ecom.services.customer.review;

import fr.doranco.ecom.dto.OrderedProductsResponseDto;
import fr.doranco.ecom.dto.ReviewDto;

import java.io.IOException;

public interface ReviewService {
    OrderedProductsResponseDto getOrderedProductsDetailsByOrderId(Long orderId);
    Boolean giveReview(ReviewDto reviewDto) throws IOException;

}
