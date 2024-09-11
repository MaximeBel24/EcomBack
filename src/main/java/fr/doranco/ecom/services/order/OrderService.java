package fr.doranco.ecom.services.order;

import fr.doranco.ecom.dto.OrderDto;

import java.util.List;

public interface OrderService {
    OrderDto createOrderFromCart(Long cartId);
    OrderDto getOrderById(Long orderId);
    List<OrderDto> getOrdersByUserId(Long userId);

    List<OrderDto> getAllOrders();
}
