package fr.doranco.ecom.services.faq;

import fr.doranco.ecom.dto.FAQDto;
import fr.doranco.ecom.entities.FAQ;
import fr.doranco.ecom.entities.Product;
import fr.doranco.ecom.exceptions.FAQException;
import fr.doranco.ecom.exceptions.ProductException;
import fr.doranco.ecom.repositories.FAQRepository;
import fr.doranco.ecom.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FAQServiceImpl implements FAQService {

    private final FAQRepository faqRepository;
    private final ProductRepository productRepository;

    @Override
    public FAQDto createFAQ(FAQDto faqDto) {
        Product product = productRepository.findById(faqDto.getProductId())
                .orElseThrow(() -> new ProductException("Produit introuvable"));

        FAQ faq = new FAQ();
        faq.setQuestion(faqDto.getQuestion());
        faq.setAnswer(faqDto.getAnswer());
        faq.setProduct(product);

        FAQ savedFAQ = faqRepository.save(faq);

        return convertToDto(savedFAQ);
    }

    @Override
    public FAQDto updateFAQ(Long id, FAQDto faqDto) {
        FAQ faq = faqRepository.findById(id)
                .orElseThrow(() -> new FAQException("FAQ introuvable"));

        faq.setQuestion(faqDto.getQuestion());
        faq.setAnswer(faqDto.getAnswer());

        FAQ updatedFAQ = faqRepository.save(faq);
        return convertToDto(updatedFAQ);
    }

    @Override
    public void deleteFAQ(Long id) {
        faqRepository.deleteById(id);
    }

    @Override
    public FAQDto getFAQById(Long id) {
        FAQ faq = faqRepository.findById(id)
                .orElseThrow(() -> new FAQException("FAQ introuvable"));
        return convertToDto(faq);
    }

    @Override
    public List<FAQDto> getFAQsByProductId(Long productId) {
        return faqRepository.findAllByProductId(productId)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private FAQDto convertToDto(FAQ faq) {
        return new FAQDto(
                faq.getId(),
                faq.getQuestion(),
                faq.getAnswer(),
                faq.getProduct().getId()
        );
    }
}
