package fr.doranco.ecom.services.order_item;

import fr.doranco.ecom.dto.OrderItemDto;
import fr.doranco.ecom.entities.Order;
import fr.doranco.ecom.entities.OrderItem;
import fr.doranco.ecom.entities.Product;
import fr.doranco.ecom.exceptions.OrderException;
import fr.doranco.ecom.exceptions.ProductException;
import fr.doranco.ecom.repositories.OrderItemRepository;
import fr.doranco.ecom.repositories.OrderRepository;
import fr.doranco.ecom.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderItemServiceImpl implements OrderItemService {

    private final OrderItemRepository orderItemRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    @Override
    public OrderItemDto addOrderItem(OrderItemDto orderItemDto, Long orderId) {
        // Récupérer la commande
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderException("Commande introuvable"));

        // Récupérer le produit
        Product product = productRepository.findById(orderItemDto.getProductId())
                .orElseThrow(() -> new ProductException("Produit introuvable"));

        // Créer un nouvel item de commande
        OrderItem orderItem = new OrderItem();
        orderItem.setOrder(order);
        orderItem.setProduct(product);
        orderItem.setQuantity(orderItemDto.getQuantity());
        orderItem.setPrice(product.getPrice());
        orderItem.setTotalPrice(product.getPrice() * orderItem.getQuantity());

        // Sauvegarder l'article dans la commande
        OrderItem savedOrderItem = orderItemRepository.save(orderItem);

        return convertToDto(savedOrderItem);
    }

    @Override
    public OrderItemDto updateOrderItem(Long id, OrderItemDto orderItemDto) {
        OrderItem orderItem = orderItemRepository.findById(id)
                .orElseThrow(() -> new OrderException("Article de commande introuvable"));

        // Mettre à jour la quantité et recalculer le prix total
        orderItem.setQuantity(orderItemDto.getQuantity());
        orderItem.setTotalPrice(orderItem.getPrice() * orderItem.getQuantity());

        // Sauvegarder les modifications
        OrderItem updatedOrderItem = orderItemRepository.save(orderItem);
        return convertToDto(updatedOrderItem);
    }

    @Override
    public void deleteOrderItem(Long id) {
        orderItemRepository.deleteById(id);
    }

    @Override
    public OrderItemDto getOrderItemById(Long id) {
        OrderItem orderItem = orderItemRepository.findById(id)
                .orElseThrow(() -> new OrderException("Article de commande introuvable"));
        return convertToDto(orderItem);
    }

    @Override
    public List<OrderItemDto> getOrderItemsByOrderId(Long orderId) {
        return orderItemRepository.findByOrderId(orderId)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // Méthode de conversion de OrderItem en OrderItemDto
    private OrderItemDto convertToDto(OrderItem orderItem) {
        OrderItemDto orderItemDto = new OrderItemDto();
        orderItemDto.setId(orderItem.getId());
        orderItemDto.setProductId(orderItem.getProduct().getId());
        orderItemDto.setQuantity(orderItem.getQuantity());
        orderItemDto.setPrice(orderItem.getPrice());
        orderItemDto.setTotalPrice(orderItem.getTotalPrice());
        return orderItemDto;
    }
}
