package fr.doranco.ecom.dto;

import fr.doranco.ecom.enums.UserRole;
import lombok.Data;

@Data
public class UserDto {

    private Long id;

    private String email;

    private String firstName;

    private String lastName;

    private String password;

    private UserRole userRole;
}
