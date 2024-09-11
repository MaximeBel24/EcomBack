package fr.doranco.ecom.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Table(name = "address")
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "La rue est obligatoire")
    private String street;

    @NotBlank(message = "La ville est obligatoire")
    private String city;

    @NotBlank(message = "Le pays est obligatoire")
    private String country;

    @NotBlank(message = "Le code postal est obligatoire")
    private String zipCode;


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}