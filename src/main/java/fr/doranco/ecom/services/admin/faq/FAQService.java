package fr.doranco.ecom.services.admin.faq;

import fr.doranco.ecom.dto.FAQDto;

public interface FAQService {

    FAQDto postFAQ(Long productId, FAQDto faqDto);
}
