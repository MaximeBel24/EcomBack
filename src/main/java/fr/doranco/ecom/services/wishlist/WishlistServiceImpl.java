package fr.doranco.ecom.services.wishlist;

import fr.doranco.ecom.dto.WishlistDto;
import fr.doranco.ecom.entities.Product;
import fr.doranco.ecom.entities.User;
import fr.doranco.ecom.entities.Wishlist;
import fr.doranco.ecom.exceptions.ProductException;
import fr.doranco.ecom.exceptions.UserException;
import fr.doranco.ecom.repositories.ProductRepository;
import fr.doranco.ecom.repositories.UserRepository;
import fr.doranco.ecom.repositories.WishlistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WishlistServiceImpl implements WishlistService {

    private final WishlistRepository wishlistRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Override
    public WishlistDto addProductToWishlist(WishlistDto wishlistDto) {
        // Récupérer l'utilisateur
        User user = userRepository.findById(wishlistDto.getUserId())
                .orElseThrow(() -> new UserException("Utilisateur introuvable"));

        // Récupérer le produit
        Product product = productRepository.findById(wishlistDto.getProductId())
                .orElseThrow(() -> new ProductException("Produit introuvable"));

        // Vérifier si l'article est déjà dans la wishlist de l'utilisateur
        boolean alreadyInWishlist = wishlistRepository.findAllByUserId(user.getId()).stream()
                .anyMatch(w -> w.getProduct().getId().equals(product.getId()));

        if (alreadyInWishlist) {
            throw new RuntimeException("Ce produit est déjà dans la liste de souhaits.");
        }

        // Créer une nouvelle entrée dans la wishlist
        Wishlist wishlist = new Wishlist();
        wishlist.setUser(user);
        wishlist.setProduct(product);

        Wishlist savedWishlist = wishlistRepository.save(wishlist);

        return convertToDto(savedWishlist);
    }

    @Override
    public void removeProductFromWishlist(Long id) {
        wishlistRepository.deleteById(id);
    }

    @Override
    public List<WishlistDto> getWishlistByUserId(Long userId) {
        return wishlistRepository.findAllByUserId(userId)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<WishlistDto> getWishlistByProductId(Long productId) {
        return wishlistRepository.findAllByProductId(productId)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // Méthode de conversion
    private WishlistDto convertToDto(Wishlist wishlist) {
        return new WishlistDto(
                wishlist.getId(),
                wishlist.getUser().getId(),
                wishlist.getProduct().getId()
        );
    }
}
