package fr.doranco.ecom.entities;

import fr.doranco.ecom.dto.WishlistDto;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Data
public class Wishlist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Product product;

    public WishlistDto getWishlistDto(){
        WishlistDto dto = new WishlistDto();

        dto.setId(id);
        dto.setProductId(product.getId());
        dto.setProductName(product.getName());
        dto.setReturnedImg(product.getImg());
        dto.setProductDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setUserId(user.getId());

        return dto;
    }
}
