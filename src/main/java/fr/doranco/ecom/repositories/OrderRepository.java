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


    List<Order> findAllByUserId(Long userId);
}
