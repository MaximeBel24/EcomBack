package fr.doranco.ecom.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "category")
@Data
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Lob
    @Column(columnDefinition = "TEXT") // Ajout explicite pour PostgreSQL
    private String description;

//    @Lob
//    private String description;
}
