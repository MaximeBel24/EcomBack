package fr.doranco.ecom.controllers;

import fr.doranco.ecom.dto.OrderItemDto;
import fr.doranco.ecom.services.order_item.OrderItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order-items")
@RequiredArgsConstructor
public class OrderItemController {

    private final OrderItemService orderItemService;

    // Ajouter un nouvel article à une commande
    @PostMapping("/{orderId}/add")
    public ResponseEntity<OrderItemDto> addOrderItem(@PathVariable Long orderId, @RequestBody OrderItemDto orderItemDto) {
        OrderItemDto addedOrderItem = orderItemService.addOrderItem(orderItemDto, orderId);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedOrderItem);
    }

    // Mettre à jour un article de commande
    @PutMapping("/{id}")
    public ResponseEntity<OrderItemDto> updateOrderItem(@PathVariable Long id, @RequestBody OrderItemDto orderItemDto) {
        OrderItemDto updatedOrderItem = orderItemService.updateOrderItem(id, orderItemDto);
        return ResponseEntity.ok(updatedOrderItem);
    }

    // Supprimer un article de commande
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrderItem(@PathVariable Long id) {
        orderItemService.deleteOrderItem(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    // Obtenir un article de commande par son ID
    @GetMapping("/{id}")
    public ResponseEntity<OrderItemDto> getOrderItemById(@PathVariable Long id) {
        OrderItemDto orderItem = orderItemService.getOrderItemById(id);
        return ResponseEntity.ok(orderItem);
    }

    // Obtenir tous les articles d'une commande
    @GetMapping("/order/{orderId}")
    public ResponseEntity<List<OrderItemDto>> getOrderItemsByOrderId(@PathVariable Long orderId) {
        List<OrderItemDto> orderItems = orderItemService.getOrderItemsByOrderId(orderId);
        return ResponseEntity.ok(orderItems);
    }
}
