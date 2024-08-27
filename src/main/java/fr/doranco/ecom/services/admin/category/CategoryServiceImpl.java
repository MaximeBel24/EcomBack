package fr.doranco.ecom.services.admin.category;

import fr.doranco.ecom.dto.CategoryDto;
import fr.doranco.ecom.entities.Category;
import fr.doranco.ecom.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService{

    private final CategoryRepository categoryRepository;

    public Category createCategory(CategoryDto categoryDto){
        Category category = new Category();
        category.setName(categoryDto.getName());
        category.setDescription(category.getDescription());

        return categoryRepository.save(category);
    }
}
