package fr.doranco.ecom.controllers;

import fr.doranco.ecom.dto.CartDto;
import fr.doranco.ecom.services.cart.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/carts")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping("/create/{userId}")
    public ResponseEntity<CartDto> createCart(@PathVariable Long userId) {
        CartDto createdCart = cartService.createCart(userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCart);
    }


    // Obtenir un panier par son ID
    @GetMapping("/{id}")
    public ResponseEntity<CartDto> getCartById(@PathVariable Long id) {
        Optional<CartDto> cart = cartService.getCartById(id);
        return cart.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // Mettre à jour un panier
    @PutMapping("/{id}")
    public ResponseEntity<CartDto> updateCart(@PathVariable Long id, @RequestBody CartDto cartDto) {
        CartDto updatedCart = cartService.updateCart(id, cartDto);
        return ResponseEntity.ok(updatedCart);
    }

    // Supprimer un panier
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCart(@PathVariable Long id) {
        cartService.deleteCart(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    // Obtenir tous les paniers
    @GetMapping
    public ResponseEntity<List<CartDto>> getAllCarts() {
        List<CartDto> carts = cartService.getAllCarts();
        return ResponseEntity.ok(carts);
    }

    // Obtenir un panier par ID utilisateur
    @GetMapping("/user/{userId}")
    public ResponseEntity<CartDto> getCartByUserId(@PathVariable Long userId) {
        CartDto cart = cartService.getCartByUserId(userId);
        return ResponseEntity.ok(cart);
    }

    // Ajouter un article au panier
    @PostMapping("/{cartId}/add-item")
    public ResponseEntity<CartDto> addCartItem(@PathVariable Long cartId, @RequestParam Long productId, @RequestParam int quantity) {
        CartDto updatedCart = cartService.addCartItem(cartId, productId, quantity);
        return ResponseEntity.ok(updatedCart);
    }
}
