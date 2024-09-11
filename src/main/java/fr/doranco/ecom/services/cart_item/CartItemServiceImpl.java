package fr.doranco.ecom.services.cart_item;

import fr.doranco.ecom.dto.CartItemDto;
import fr.doranco.ecom.entities.Cart;
import fr.doranco.ecom.entities.CartItem;
import fr.doranco.ecom.entities.Product;
import fr.doranco.ecom.exceptions.CartException;
import fr.doranco.ecom.exceptions.ProductException;
import fr.doranco.ecom.repositories.CartItemRepository;
import fr.doranco.ecom.repositories.CartRepository;
import fr.doranco.ecom.repositories.ProductRepository;
import fr.doranco.ecom.services.cart.CartServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartItemServiceImpl implements CartItemService {

    private final CartItemRepository cartItemRepository;
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final CartServiceImpl cartService;

    @Override
    public CartItemDto addCartItem(CartItemDto cartItemDto, Long cartId) {
        // Récupérer le panier
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new CartException("Panier introuvable"));

        // Récupérer le produit
        Product product = productRepository.findById(cartItemDto.getProductId())
                .orElseThrow(() -> new ProductException("Produit introuvable"));

        // Vérifier si l'article existe déjà dans le panier
        CartItem existingCartItem = cart.getCartItems().stream()
                .filter(item -> item.getProduct().getId().equals(product.getId()))
                .findFirst()
                .orElse(null);

        CartItem savedCartItem;

        if (existingCartItem != null) {
            // Si l'article est déjà présent, augmenter la quantité
            existingCartItem.setQuantity(existingCartItem.getQuantity() + cartItemDto.getQuantity());
            existingCartItem.setTotalPrice(existingCartItem.getPrice() * existingCartItem.getQuantity());
            savedCartItem = cartItemRepository.save(existingCartItem);
        } else {
            // Créer un nouvel article dans le panier
            CartItem newCartItem = new CartItem();
            newCartItem.setCart(cart);
            newCartItem.setProduct(product);
            newCartItem.setQuantity(cartItemDto.getQuantity());
            newCartItem.setPrice(product.getPrice());
            newCartItem.setTotalPrice(product.getPrice() * newCartItem.getQuantity());

            // Ajouter l'article au panier et sauvegarder
            cart.getCartItems().add(newCartItem);
            savedCartItem = cartItemRepository.save(newCartItem);
        }

        // Mettre à jour le totalPrice du panier
        cartService.updateCartTotalPrice(cart);

        // Retourner le CartItem mis à jour ou nouvellement créé sous forme de DTO
        return convertToDto(savedCartItem);
    }


    @Override
    public CartItemDto updateCartItem(Long id, CartItemDto cartItemDto) {
        // Récupérer l'article du panier
        CartItem cartItem = cartItemRepository.findById(id)
                .orElseThrow(() -> new CartException("Article du panier introuvable"));

        // Mettre à jour la quantité
        cartItem.setQuantity(cartItemDto.getQuantity());

        // Recalculer le totalPrice de cet article
        cartItem.setTotalPrice(cartItem.getPrice() * cartItem.getQuantity());

        // Sauvegarder les modifications
        cartItemRepository.save(cartItem);

        // Mettre à jour le totalPrice du panier après la modification de l'article
        cartService.updateCartTotalPrice(cartItem.getCart());

        return convertToDto(cartItem);
    }

    @Override
    public void deleteCartItem(Long id) {
        CartItem cartItem = cartItemRepository.findById(id)
                .orElseThrow(() -> new CartException("Article du panier introuvable"));

        cartItemRepository.delete(cartItem);

        // Mettre à jour le totalPrice du panier après suppression de l'article
        cartService.updateCartTotalPrice(cartItem.getCart());
    }

    @Override
    public CartItemDto getCartItemById(Long id) {
        CartItem cartItem = cartItemRepository.findById(id)
                .orElseThrow(() -> new CartException("Article du panier introuvable"));
        return convertToDto(cartItem);
    }

    @Override
    public List<CartItemDto> getCartItemsByCartId(Long cartId) {
        return cartItemRepository.findByCartId(cartId)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public CartItemDto increaseQuantity(Long id) {
        CartItem cartItem = cartItemRepository.findById(id)
                .orElseThrow(() -> new CartException("Article du panier introuvable"));

        // Augmenter la quantité
        cartItem.setQuantity(cartItem.getQuantity() + 1);
        cartItem.setTotalPrice(cartItem.getPrice() * cartItem.getQuantity());

        CartItem updatedItem = cartItemRepository.save(cartItem);

        // Mettre à jour le totalPrice du panier après l'augmentation de la quantité
        cartService.updateCartTotalPrice(cartItem.getCart());

        return convertToDto(updatedItem);
    }

    @Override
    public CartItemDto decreaseQuantity(Long id) {
        CartItem cartItem = cartItemRepository.findById(id)
                .orElseThrow(() -> new CartException("Article du panier introuvable"));

        if (cartItem.getQuantity() > 1) {
            // Réduire la quantité
            cartItem.setQuantity(cartItem.getQuantity() - 1);
            cartItem.setTotalPrice(cartItem.getPrice() * cartItem.getQuantity());
        } else {
            throw new CartException("La quantité ne peut pas être inférieure à 1");
        }

        CartItem updatedItem = cartItemRepository.save(cartItem);

        // Mettre à jour le totalPrice du panier après la réduction de la quantité
        cartService.updateCartTotalPrice(cartItem.getCart());

        return convertToDto(updatedItem);
    }

    // Méthode de conversion de CartItem en CartItemDto
    private CartItemDto convertToDto(CartItem cartItem) {
        CartItemDto cartItemDto = new CartItemDto();
        cartItemDto.setId(cartItem.getId());
        cartItemDto.setProductId(cartItem.getProduct().getId());
        cartItemDto.setQuantity(cartItem.getQuantity());
        cartItemDto.setPrice(cartItem.getPrice());
        cartItemDto.setTotalPrice(cartItem.getTotalPrice());
        return cartItemDto;
    }
}
