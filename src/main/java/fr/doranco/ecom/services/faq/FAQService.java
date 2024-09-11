package fr.doranco.ecom.services.faq;

import fr.doranco.ecom.dto.FAQDto;

import java.util.List;

public interface FAQService {
    FAQDto createFAQ(FAQDto faqDto);
    FAQDto updateFAQ(Long id, FAQDto faqDto);
    void deleteFAQ(Long id);
    FAQDto getFAQById(Long id);
    List<FAQDto> getFAQsByProductId(Long productId);
}
