package fr.doranco.ecom.entities;

import fr.doranco.ecom.enums.UserRole;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String password;

    private String firstName;

    private String lastName;

    private UserRole role;

    @Lob
    @Column(columnDefinition = "longblob")
    private byte[] img;

}
