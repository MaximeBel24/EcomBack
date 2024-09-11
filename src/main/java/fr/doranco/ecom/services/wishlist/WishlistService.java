package fr.doranco.ecom.services.wishlist;

import fr.doranco.ecom.dto.WishlistDto;

import java.util.List;

public interface WishlistService {
    WishlistDto addProductToWishlist(WishlistDto wishlistDto);
    void removeProductFromWishlist(Long id);
    List<WishlistDto> getWishlistByUserId(Long userId);
    List<WishlistDto> getWishlistByProductId(Long productId);
}
