package fr.doranco.ecom.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDto {

    private Long id;
    private Long rating;
    private String description;
    private byte[] img;
    private Long userId;
    private Long productId;
    private String reviewDate;
}
