package fr.doranco.ecom.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FAQDto {

    private Long id;
    private String question;
    private String answer;
    private Long productId;
}
