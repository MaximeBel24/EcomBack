package fr.doranco.ecom.services.admin.adminOrder;

import fr.doranco.ecom.dto.OrderDto;
import fr.doranco.ecom.entities.Order;
import fr.doranco.ecom.enums.OrderStatus;
import fr.doranco.ecom.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminOrderServiceImpl implements AdminOrderService{

    private final OrderRepository orderRepository;

    @Override
    public List<OrderDto> getAllPlacedOrders(){
        List<Order> orderList = orderRepository.findAllByOrderStatusIn(
                List.of(OrderStatus.Placed, OrderStatus.Shipped, OrderStatus.Delivered)
        );

        return orderList.stream().map(Order::getOrderDto).toList();
    }

    @Override
    public OrderDto changeOrderStatus(Long orderId, String status){
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if(optionalOrder.isPresent()){
            Order order = optionalOrder.get();

            if (Objects.equals(status, "Shipped")){
                order.setOrderStatus(OrderStatus.Shipped);
            } else {
                order.setOrderStatus(OrderStatus.Delivered);
            }

            return orderRepository.save(order).getOrderDto();
        }
        return null;
    }
}
