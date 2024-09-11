package fr.doranco.ecom.controllers;

import fr.doranco.ecom.dto.WishlistDto;
import fr.doranco.ecom.services.wishlist.WishlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/wishlists")
@RequiredArgsConstructor
public class WishlistController {

    private final WishlistService wishlistService;

    @PostMapping
    public ResponseEntity<WishlistDto> addProductToWishlist(@RequestBody WishlistDto wishlistDto) {
        WishlistDto createdWishlist = wishlistService.addProductToWishlist(wishlistDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdWishlist);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeProductFromWishlist(@PathVariable Long id) {
        wishlistService.removeProductFromWishlist(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<WishlistDto>> getWishlistByUserId(@PathVariable Long userId) {
        List<WishlistDto> wishlist = wishlistService.getWishlistByUserId(userId);
        return ResponseEntity.ok(wishlist);
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<WishlistDto>> getWishlistByProductId(@PathVariable Long productId) {
        List<WishlistDto> wishlist = wishlistService.getWishlistByProductId(productId);
        return ResponseEntity.ok(wishlist);
    }
}
