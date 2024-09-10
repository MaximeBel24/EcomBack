package fr.doranco.ecom.repositories;

import fr.doranco.ecom.entities.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WishlistRepository extends JpaRepository<Wishlist, Long> {

    List<Wishlist> findAllByUserId(Long userId);
    List<Wishlist> findAllByProductId(Long productId);
}
