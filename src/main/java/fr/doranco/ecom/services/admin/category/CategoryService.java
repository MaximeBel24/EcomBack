package fr.doranco.ecom.services.admin.category;

import fr.doranco.ecom.dto.CategoryDto;
import fr.doranco.ecom.entities.Category;

public interface CategoryService {

    Category createCategory(CategoryDto categoryDto);
}
