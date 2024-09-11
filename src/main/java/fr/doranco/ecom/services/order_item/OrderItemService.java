package fr.doranco.ecom.services.order_item;

import fr.doranco.ecom.dto.OrderItemDto;
import java.util.List;

public interface OrderItemService {
    OrderItemDto addOrderItem(OrderItemDto orderItemDto, Long orderId);
    OrderItemDto updateOrderItem(Long id, OrderItemDto orderItemDto);
    void deleteOrderItem(Long id);
    OrderItemDto getOrderItemById(Long id);
    List<OrderItemDto> getOrderItemsByOrderId(Long orderId);
}
