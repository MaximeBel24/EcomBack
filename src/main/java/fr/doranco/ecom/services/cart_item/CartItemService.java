package fr.doranco.ecom.services.cart_item;

import fr.doranco.ecom.dto.CartItemDto;

import java.util.List;

public interface CartItemService {
    CartItemDto addCartItem(CartItemDto cartItemDto, Long cartId);
    CartItemDto updateCartItem(Long id, CartItemDto cartItemDto);
    void deleteCartItem(Long id);
    CartItemDto getCartItemById(Long id);
    List<CartItemDto> getCartItemsByCartId(Long cartId);

    CartItemDto increaseQuantity(Long id);
    CartItemDto decreaseQuantity(Long id);
}
