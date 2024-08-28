package fr.doranco.ecom.services.admin.adminProduct;

import fr.doranco.ecom.dto.ProductDto;
import fr.doranco.ecom.entities.Product;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface AdminProductService {

    ProductDto addProduct(ProductDto productDto) throws IOException;

    List<ProductDto> getAllProducts();

    List<ProductDto> getAllProductByName(String name);

    boolean deleteProduct(Long id);

    Optional<Product> getProductById(Long id);
}
