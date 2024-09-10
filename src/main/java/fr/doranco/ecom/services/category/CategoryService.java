package fr.doranco.ecom.services.category;

import fr.doranco.ecom.entities.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    Category createCategory(Category category);

    Category updateCategory(Long id, Category updatedCategory);

    void deleteCategory(Long id);

    Optional<Category> getCategoryById(Long id);

    List<Category> getAllCategories();

    Category getCategoryByName(String name);

    List<Category> getSubCategories(Long parentCategoryId);
}
