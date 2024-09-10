package fr.doranco.ecom.repositories;

import fr.doranco.ecom.entities.User;
import fr.doranco.ecom.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findFirstByEmail(String email);

    User findByRole(UserRole userRole);
    boolean existsByEmail(String email);
    User findByEmail(String email);
}
