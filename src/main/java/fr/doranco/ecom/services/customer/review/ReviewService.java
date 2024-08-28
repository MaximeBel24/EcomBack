package fr.doranco.ecom.services.customer.review;

import fr.doranco.ecom.dto.OrderedProductsResponseDto;

public interface ReviewService {
    OrderedProductsResponseDto getOrderedProductsDetailsByOrderId(Long orderId);
}
