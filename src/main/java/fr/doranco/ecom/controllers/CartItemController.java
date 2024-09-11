package fr.doranco.ecom.controllers;

import fr.doranco.ecom.dto.CartItemDto;
import fr.doranco.ecom.services.cart_item.CartItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart-items")
@RequiredArgsConstructor
public class CartItemController {

    private final CartItemService cartItemService;

    @PostMapping("/{cartId}/add")
    public ResponseEntity<CartItemDto> addCartItem(@PathVariable Long cartId, @RequestBody CartItemDto cartItemDto) {
        CartItemDto addedCartItem = cartItemService.addCartItem(cartItemDto, cartId);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedCartItem);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CartItemDto> updateCartItem(@PathVariable Long id, @RequestBody CartItemDto cartItemDto) {
        CartItemDto updatedCartItem = cartItemService.updateCartItem(id, cartItemDto);
        return ResponseEntity.ok(updatedCartItem);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCartItem(@PathVariable Long id) {
        cartItemService.deleteCartItem(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CartItemDto> getCartItemById(@PathVariable Long id) {
        CartItemDto cartItem = cartItemService.getCartItemById(id);
        return ResponseEntity.ok(cartItem);
    }

    @GetMapping("/cart/{cartId}")
    public ResponseEntity<List<CartItemDto>> getCartItemsByCartId(@PathVariable Long cartId) {
        List<CartItemDto> cartItems = cartItemService.getCartItemsByCartId(cartId);
        return ResponseEntity.ok(cartItems);
    }

    @PutMapping("/{id}/increase")
    public ResponseEntity<CartItemDto> increaseQuantity(@PathVariable Long id) {
        CartItemDto updatedCartItem = cartItemService.increaseQuantity(id);
        return ResponseEntity.ok(updatedCartItem);
    }

    @PutMapping("/{id}/decrease")
    public ResponseEntity<CartItemDto> decreaseQuantity(@PathVariable Long id) {
        CartItemDto updatedCartItem = cartItemService.decreaseQuantity(id);
        return ResponseEntity.ok(updatedCartItem);
    }
}
