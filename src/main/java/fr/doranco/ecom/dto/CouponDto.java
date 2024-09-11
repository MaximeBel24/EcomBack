package fr.doranco.ecom.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CouponDto {
    private Long id;
    private String name;
    private String code;
    private Long discount;
    private Date expirationDate;
}
