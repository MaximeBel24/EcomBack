package fr.doranco.ecom.services.customer;

import fr.doranco.ecom.dto.ProductDto;

import java.util.List;

public interface CustomerProductService {

    List<ProductDto> searchProductByTitle(String title);

    List<ProductDto> getAllProducts();
}
