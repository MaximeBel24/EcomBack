package fr.doranco.ecom.services.order;

import fr.doranco.ecom.dto.OrderDto;
import fr.doranco.ecom.dto.OrderItemDto;
import fr.doranco.ecom.entities.Cart;
import fr.doranco.ecom.entities.Order;
import fr.doranco.ecom.entities.OrderItem;
import fr.doranco.ecom.enums.OrderStatus;
import fr.doranco.ecom.exceptions.CartException;
import fr.doranco.ecom.repositories.CartRepository;
import fr.doranco.ecom.repositories.OrderRepository;
import fr.doranco.ecom.services.cart.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final CartService cartService;

    @Override
    @Transactional
    public OrderDto createOrderFromCart(Long cartId) {
        // Récupérer le panier
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new CartException("Panier introuvable"));

        // Créer une nouvelle commande
        Order order = new Order();
        order.setUser(cart.getUser());
        order.setOrderDate(new Date());
        order.setTotalAmount(cart.getTotalPrice());
        order.setOrderStatus(OrderStatus.Pending);

        // Créer les articles de la commande à partir des articles du panier
        List<OrderItem> orderItems = cart.getCartItems().stream().map(cartItem -> {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(cartItem.getPrice());
            orderItem.setTotalPrice(cartItem.getTotalPrice());
            return orderItem;
        }).collect(Collectors.toList());

        order.setOrderItems(orderItems);

        // Sauvegarder la commande
        Order savedOrder = orderRepository.save(order);

        // Vider le panier après la commande
        cartService.clearCart(cartId);

        return convertToDto(savedOrder);
    }

    @Override
    public OrderDto getOrderById(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new CartException("Commande introuvable"));
        return convertToDto(order);
    }

    @Override
    public List<OrderDto> getOrdersByUserId(Long userId) {
        List<Order> orders = orderRepository.findAllByUserId(userId);
        return orders.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Override
    public List<OrderDto> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    // Méthode de conversion de Order en OrderDto
    private OrderDto convertToDto(Order order) {
        List<OrderItemDto> orderItemsDto = order.getOrderItems().stream().map(orderItem -> new OrderItemDto(
                orderItem.getId(),
                orderItem.getProduct().getId(),
                orderItem.getQuantity(),
                orderItem.getPrice(),
                orderItem.getTotalPrice()
        )).collect(Collectors.toList());

        return new OrderDto(
                order.getId(),
                order.getUser().getId(),
                order.getOrderDate(),
                order.getTotalAmount(),
                order.getOrderStatus(),
                orderItemsDto
        );
    }
}
