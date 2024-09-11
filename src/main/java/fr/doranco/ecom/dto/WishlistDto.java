package fr.doranco.ecom.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WishlistDto {

    private Long id;
    private Long userId;
    private Long productId;
}
