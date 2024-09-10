package fr.doranco.ecom.services.user;

import fr.doranco.ecom.dto.SignupRequest;
import fr.doranco.ecom.dto.UserDto;

import java.util.List;

public interface UserService {
    UserDto createUser(SignupRequest signupRequest);

    UserDto getUserById(Long userId);  // Récupérer un utilisateur par ID

    UserDto getUserByEmail(String email);  // Récupérer un utilisateur par email

    List<UserDto> getAllUsers();  // Récupérer la liste de tous les utilisateurs

    UserDto updateUser(Long userId, UserDto userDto);  // Mise à jour d'un utilisateur

    void deleteUser(Long userId);  // Suppression d'un utilisateur

    boolean existsByEmail(String email);  // Vérifier si un utilisateur existe par email
}
