package fr.doranco.ecom.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartDto {
    private Long id;
    private Long userId;
    private List<CartItemDto> cartItems;
    private Long totalPrice;
    private Date createdAt;
    private Date updatedAt;
}
