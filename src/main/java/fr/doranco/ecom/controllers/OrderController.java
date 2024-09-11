package fr.doranco.ecom.controllers;

import fr.doranco.ecom.dto.OrderDto;
import fr.doranco.ecom.services.order.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    // Créer une commande à partir d'un panier
    @PostMapping("/create/{cartId}")
    public ResponseEntity<OrderDto> createOrderFromCart(@PathVariable Long cartId) {
        OrderDto createdOrder = orderService.createOrderFromCart(cartId);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdOrder);
    }

    // Récupérer une commande par son ID
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDto> getOrderById(@PathVariable Long orderId) {
        OrderDto order = orderService.getOrderById(orderId);
        return ResponseEntity.ok(order);
    }

    // Récupérer les commandes d'un utilisateur
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<OrderDto>> getOrdersByUserId(@PathVariable Long userId) {
        List<OrderDto> orders = orderService.getOrdersByUserId(userId);
        return ResponseEntity.ok(orders);
    }

    // Récupérer toutes les commandes
    @GetMapping
    public ResponseEntity<List<OrderDto>> getAllOrders() {
        List<OrderDto> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }
}
