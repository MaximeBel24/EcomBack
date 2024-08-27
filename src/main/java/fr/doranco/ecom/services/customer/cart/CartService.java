package fr.doranco.ecom.services.customer.cart;

import fr.doranco.ecom.dto.AddProductInCartDto;
import fr.doranco.ecom.dto.OrderDto;
import org.springframework.http.ResponseEntity;

public interface CartService {

    ResponseEntity<?> addProductToCart(AddProductInCartDto addProductInCartDto);

    OrderDto getCartByUserId(Long userId);
}
