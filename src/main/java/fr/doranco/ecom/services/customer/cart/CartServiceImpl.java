package fr.doranco.ecom.services.customer.cart;

import fr.doranco.ecom.dto.AddProductInCartDto;
import fr.doranco.ecom.entities.CartItems;
import fr.doranco.ecom.entities.Order;
import fr.doranco.ecom.entities.Product;
import fr.doranco.ecom.entities.User;
import fr.doranco.ecom.enums.OrderStatus;
import fr.doranco.ecom.repositories.CartItemsRepository;
import fr.doranco.ecom.repositories.OrderRepository;
import fr.doranco.ecom.repositories.ProductRepository;
import fr.doranco.ecom.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartServiceImpl implements CartService{

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartItemsRepository cartItemsRepository;

    @Autowired
    private ProductRepository productRepository;

    public ResponseEntity<?> addProductToCart(AddProductInCartDto addProductInCartDto){
        Order activeOrder = orderRepository.findByUserIdAndStatus(addProductInCartDto.getUserId(), OrderStatus.Pending);
        Optional<CartItems> optionalCartItems = cartItemsRepository.findByProductIdAndOrderIdAndUserId(
                addProductInCartDto.getProductId(),
                activeOrder.getId(),
                addProductInCartDto.getUserId()
        )

                if(optionalCartItems.isPresent()){
                    return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
                } else {
                    Optional<Product> optionalProduct = productRepository.findById(addProductInCartDto.getProductId());
                    Optional<User> optionalUser = userRepository.findById(addProductInCartDto.getUserId());

                    if(optionalProduct.isPresent() && optionalUser.isPresent()){

                    } else {
                        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User or product not found");
//                        reprendre a 2h 43 min
                    }
                }
    }

}
