package fr.doranco.ecom.dto;

import fr.doranco.ecom.entities.User;
import fr.doranco.ecom.enums.OrderStatus;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
public class OrderDto {

    private Long id;

    private String orderDescription;

    private Date date;

    private Long amount;

    private String address;

    private String payment;

    private OrderStatus orderStatus;

    private Long totalAmount;

    private Long discount;

    private UUID trackingId;

    private String userFirstName;

    private String userLastName;

    private List<CartItemsDto> cartItems;

    private String couponName;
}
