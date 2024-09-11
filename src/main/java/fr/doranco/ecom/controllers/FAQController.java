package fr.doranco.ecom.controllers;

import fr.doranco.ecom.dto.FAQDto;
import fr.doranco.ecom.services.faq.FAQService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/faqs")
@RequiredArgsConstructor
public class FAQController {

    private final FAQService faqService;

    // Créer une nouvelle FAQ
    @PostMapping
    public ResponseEntity<FAQDto> createFAQ(@RequestBody FAQDto faqDto) {
        FAQDto createdFAQ = faqService.createFAQ(faqDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdFAQ);
    }

    // Mettre à jour une FAQ
    @PutMapping("/{id}")
    public ResponseEntity<FAQDto> updateFAQ(@PathVariable Long id, @RequestBody FAQDto faqDto) {
        FAQDto updatedFAQ = faqService.updateFAQ(id, faqDto);
        return ResponseEntity.ok(updatedFAQ);
    }

    // Supprimer une FAQ
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFAQ(@PathVariable Long id) {
        faqService.deleteFAQ(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    // Obtenir une FAQ par son ID
    @GetMapping("/{id}")
    public ResponseEntity<FAQDto> getFAQById(@PathVariable Long id) {
        FAQDto faq = faqService.getFAQById(id);
        return ResponseEntity.ok(faq);
    }

    // Obtenir toutes les FAQ d'un produit
    @GetMapping("/product/{productId}")
    public ResponseEntity<List<FAQDto>> getFAQsByProductId(@PathVariable Long productId) {
        List<FAQDto> faqs = faqService.getFAQsByProductId(productId);
        return ResponseEntity.ok(faqs);
    }
}
