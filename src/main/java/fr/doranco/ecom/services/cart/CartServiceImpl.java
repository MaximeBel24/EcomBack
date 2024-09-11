package fr.doranco.ecom.services.cart;

import fr.doranco.ecom.dto.CartDto;
import fr.doranco.ecom.dto.CartItemDto;
import fr.doranco.ecom.entities.Cart;
import fr.doranco.ecom.entities.CartItem;
import fr.doranco.ecom.entities.Coupon;
import fr.doranco.ecom.entities.Product;
import fr.doranco.ecom.entities.User;
import fr.doranco.ecom.exceptions.CartException;
import fr.doranco.ecom.exceptions.CouponException;
import fr.doranco.ecom.exceptions.ProductException;
import fr.doranco.ecom.repositories.CartItemRepository;
import fr.doranco.ecom.repositories.CartRepository;
import fr.doranco.ecom.repositories.CouponRepository;
import fr.doranco.ecom.repositories.ProductRepository;
import fr.doranco.ecom.repositories.UserRepository;
import fr.doranco.ecom.utils.DateUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final CouponRepository couponRepository;  // Ajout du repository des coupons

    @Override
    public CartDto createCart(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CartException("User not found"));

        Cart cart = new Cart();
        cart.setUser(user);
        cart.setTotalPrice(0L);  // Initially empty cart
        cart.setCartItems(new ArrayList<>());
        cart.setCreatedAt(DateUtil.convertLocalDateToDate(LocalDate.now()));

        Cart savedCart = cartRepository.save(cart);
        return convertToDto(savedCart);
    }

    @Override
    public CartDto addCartItem(Long cartId, Long productId, int quantity) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new CartException("Cart not found"));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductException("Product not found"));

        // Vérifier si l'article existe déjà dans le panier
        CartItem existingCartItem = cart.getCartItems().stream()
                .filter(cartItem -> cartItem.getProduct().getId().equals(productId))
                .findFirst()
                .orElse(null);

        if (existingCartItem != null) {
            // Si l'article existe déjà, augmenter la quantité
            existingCartItem.setQuantity(existingCartItem.getQuantity() + quantity);
            existingCartItem.setTotalPrice(existingCartItem.getPrice() * existingCartItem.getQuantity());
        } else {
            // Sinon, ajouter un nouvel article au panier
            CartItem cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
            cartItem.setPrice(product.getPrice());
            cartItem.setTotalPrice(product.getPrice() * quantity);

            cart.getCartItems().add(cartItem);
        }

        // Mettre à jour le prix total du panier
        cart.setTotalPrice(calculateCartTotalPrice(cart));
        cart.setUpdatedAt(DateUtil.convertLocalDateToDate(LocalDate.now()));

        // Sauvegarder les changements
        cartRepository.save(cart);

        return convertToDto(cart);
    }

    @Override
    public CartDto applyCoupon(Long cartId, String couponCode) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new CartException("Cart not found"));

        Coupon coupon = couponRepository.findByCode(couponCode)
                .orElseThrow(() -> new CouponException("Coupon not found"));

        // Vérifier si le coupon est expiré
        if (coupon.getExpirationDate().before(new java.util.Date())) {
            throw new CouponException("Coupon has expired");
        }

        // Calculer la réduction
        Long discount = coupon.getDiscount();
        Long totalPrice = cart.getTotalPrice();
        Long discountAmount = totalPrice * discount / 100;

        // Appliquer la réduction
        cart.setTotalPrice(totalPrice - discountAmount);
        cartRepository.save(cart);

        return convertToDto(cart);
    }

    private Long calculateCartTotalPrice(Cart cart) {
        return cart.getCartItems().stream()
                .mapToLong(CartItem::getTotalPrice)
                .sum();
    }

    @Override
    public CartDto updateCart(Long id, CartDto cartDto) {
        Cart cart = cartRepository.findById(id)
                .orElseThrow(() -> new CartException("Cart not found"));

        cart.setUpdatedAt(new java.util.Date());

        Cart updatedCart = cartRepository.save(cart);
        return convertToDto(updatedCart);
    }

    @Override
    public void deleteCart(Long id) {
        Cart cart = cartRepository.findById(id)
                .orElseThrow(() -> new CartException("Cart not found"));
        cartRepository.delete(cart);
    }

    @Override
    public Optional<CartDto> getCartById(Long id) {
        return cartRepository.findById(id).map(this::convertToDto);
    }

    @Override
    public List<CartDto> getAllCarts() {
        return cartRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public CartDto getCartByUserId(Long userId) {
        Cart cart = cartRepository.findByUserId(userId);
        return convertToDto(cart);
    }

    @Override
    @Transactional
    public void clearCart(Long cartId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new CartException("Panier introuvable"));

        // Supprimer tous les articles du panier
        cart.getCartItems().clear();

        // Remettre le total du panier à zéro
        cart.setTotalPrice(0L);

        // Sauvegarder les changements
        cartRepository.save(cart);
    }

    public void updateCartTotalPrice(Cart cart) {
        Long totalPrice = cart.getCartItems().stream() // Parcourt tous les CartItems
                .mapToLong(CartItem::getTotalPrice)    // Récupère le totalPrice de chaque item
                .sum();                                // Additionne les totalPrice des items

        cart.setTotalPrice(totalPrice);  // Met à jour le totalPrice du panier
        cartRepository.save(cart);       // Sauvegarde le panier mis à jour dans la base de données
    }

    // Méthode pour convertir Cart en CartDto
    private CartDto convertToDto(Cart cart) {
        List<CartItemDto> cartItemsDto = cart.getCartItems().stream()
                .map(this::convertCartItemToDto)
                .collect(Collectors.toList());

        return new CartDto(
                cart.getId(),
                cart.getUser().getId(),
                cartItemsDto,
                cart.getTotalPrice(),
                cart.getCreatedAt(),
                cart.getUpdatedAt()
        );
    }

    // Méthode pour convertir CartItem en CartItemDto
    private CartItemDto convertCartItemToDto(CartItem cartItem) {
        return new CartItemDto(
                cartItem.getId(),
                cartItem.getProduct().getId(),
                cartItem.getQuantity(),
                cartItem.getPrice(),
                cartItem.getTotalPrice()
        );
    }
}
