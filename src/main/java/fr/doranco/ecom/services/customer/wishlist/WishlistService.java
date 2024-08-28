package fr.doranco.ecom.services.customer.wishlist;

import fr.doranco.ecom.dto.WishlistDto;

public interface WishlistService {

    WishlistDto addProductToWishlist(WishlistDto wishlistDto);
}
