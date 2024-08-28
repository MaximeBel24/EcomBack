package fr.doranco.ecom.services.customer.wishlist;

import fr.doranco.ecom.dto.WishlistDto;
import fr.doranco.ecom.entities.Product;
import fr.doranco.ecom.entities.User;
import fr.doranco.ecom.entities.Wishlist;
import fr.doranco.ecom.repositories.ProductRepository;
import fr.doranco.ecom.repositories.UserRepository;
import fr.doranco.ecom.repositories.WishlistRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class WishlistServiceImpl implements WishlistService{

    private final WishlistRepository wishlistRepository;

    private final UserRepository userRepository;

    private final ProductRepository productRepository;

    public WishlistDto addProductToWishlist(WishlistDto wishlistDto){
        Optional<Product> optionalProduct = productRepository.findById(wishlistDto.getProductId());
        Optional<User> optionalUser = userRepository.findById(wishlistDto.getUserId());

        if(optionalProduct.isPresent() && optionalUser.isPresent()){
            Wishlist wishlist = new Wishlist();
            wishlist.setProduct(optionalProduct.get());
            wishlist.setUser(optionalUser.get());

            return wishlistRepository.save(wishlist).getWishlistDto();
        }
        return null;
    }
}
