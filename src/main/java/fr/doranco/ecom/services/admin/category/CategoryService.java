package fr.doranco.ecom.services.admin.category;

import fr.doranco.ecom.dto.CategoryDto;
import fr.doranco.ecom.entities.Category;

import java.util.List;

public interface CategoryService {

    Category createCategory(CategoryDto categoryDto);

    List<Category> getAllCategories();
}
