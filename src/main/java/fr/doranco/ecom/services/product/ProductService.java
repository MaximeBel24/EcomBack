package fr.doranco.ecom.services.product;

import fr.doranco.ecom.dto.ProductDto;
import java.util.List;

public interface ProductService {
    List<ProductDto> getAllProducts();
    ProductDto getProductById(Long id);
    List<ProductDto> searchProductsByName(String name);
    List<ProductDto> getProductsByCategory(Long categoryId);
    ProductDto createProduct(ProductDto productDto);
    ProductDto updateProduct(Long id, ProductDto productDto);
    void deleteProduct(Long id);
}
