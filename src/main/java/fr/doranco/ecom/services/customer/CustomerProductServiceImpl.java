package fr.doranco.ecom.services.customer;

import fr.doranco.ecom.dto.ProductDetailDto;
import fr.doranco.ecom.dto.ProductDto;
import fr.doranco.ecom.entities.FAQ;
import fr.doranco.ecom.entities.Product;
import fr.doranco.ecom.entities.Review;
import fr.doranco.ecom.repositories.FAQRepository;
import fr.doranco.ecom.repositories.ProductRepository;
import fr.doranco.ecom.repositories.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerProductServiceImpl implements CustomerProductService{

    private final ProductRepository productRepository;

    private final FAQRepository faqRepository;

    private final ReviewRepository reviewRepository;

    @Override
    public List<ProductDto> searchProductByTitle(String name) {
        List<Product> products = productRepository.findAllByNameContaining(name);
        return products.stream().map(Product::getDto).collect(Collectors.toList());
    }

    @Override
    public List<ProductDto> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream().map(Product::getDto).collect(Collectors.toList());
    }

    public ProductDetailDto getProductDetailById(Long productId){
        Optional<Product> optionalProduct = productRepository.findById(productId);

        if(optionalProduct.isPresent()){
            Product product = optionalProduct.get();

            List<FAQ> faqList = faqRepository.findAllByProductId(productId);
            List<Review> reviewList = reviewRepository.findAllByProductId(productId);

            ProductDetailDto productDetailDto = new ProductDetailDto();
            productDetailDto.setProductDto(product.getDto());
            productDetailDto.setFaqDtoList(faqList.stream().map(FAQ::getFAQDto).toList());
            productDetailDto.setReviewDtoList(reviewList.stream().map(Review::getDto).toList());

            return productDetailDto;
        }
        return null;
    }
}
