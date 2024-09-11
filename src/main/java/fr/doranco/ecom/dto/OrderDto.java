package fr.doranco.ecom.dto;

import fr.doranco.ecom.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {
    private Long id;
    private Long userId;
    private Date orderDate;
    private Long totalAmount;
    private OrderStatus orderStatus;
    private List<OrderItemDto> orderItems;
}