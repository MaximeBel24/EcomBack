package fr.doranco.ecom.services.product;

import fr.doranco.ecom.entities.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<Product> getAllProducts();
    Optional<Product> getProductById(Long id);
    List<Product> searchProductsByName(String name);
    List<Product> getProductsByCategory(Long categoryId);
    Product createProduct(Product product);
    Product updateProduct(Long id, Product product);
    void deleteProduct(Long id);
}
