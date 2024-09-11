package fr.doranco.ecom.services.cart;

import fr.doranco.ecom.dto.CartDto;

import java.util.List;
import java.util.Optional;

public interface CartService {
    CartDto createCart(Long userId);
    CartDto addCartItem(Long cartId, Long productId, int quantity);
    CartDto updateCart(Long id, CartDto cartDto);
    void deleteCart(Long id);
    Optional<CartDto> getCartById(Long id);
    List<CartDto> getAllCarts();
    CartDto getCartByUserId(Long userId);
    CartDto applyCoupon(Long cartId, String couponCode);
    void clearCart(Long cartId);
}