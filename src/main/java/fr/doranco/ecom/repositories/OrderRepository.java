package fr.doranco.ecom.repositories;

import fr.doranco.ecom.entities.Order;
import fr.doranco.ecom.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    Order findByUserIdAndOrderStatus(Long userId, OrderStatus orderStatus);

    List<Order> findAllByOrderStatusIn(List<OrderStatus> orderStatusList);

    List<Order> findAllByUserIdAndOrderStatusIn(Long userId, List<OrderStatus> orderStatusList);

    Optional<Order> findByTrackingId(UUID trackingId);

    Long countByOrderStatus(OrderStatus status);

    List<Order> findByDateBetweenAndOrderStatus(Date startOfMonth, Date endOfMonth, OrderStatus orderStatus);

    List<Order> findAllByUserId(Long userId);
}
