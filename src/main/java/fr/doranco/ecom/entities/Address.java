package fr.doranco.ecom.entities;

import jakarta.persistence.*;
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

    private String street;

    private String city;

    private String country;

    private String zipCode;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}

