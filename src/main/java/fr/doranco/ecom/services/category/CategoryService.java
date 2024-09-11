package fr.doranco.ecom.services.category;

import fr.doranco.ecom.dto.CategoryDto;
import java.util.List;

public interface CategoryService {
    CategoryDto createCategory(CategoryDto categoryDto);
    CategoryDto updateCategory(Long id, CategoryDto updatedCategoryDto);
    void deleteCategory(Long id);
    CategoryDto getCategoryById(Long id);
    List<CategoryDto> getAllCategories();
    CategoryDto getCategoryByName(String name);
    List<CategoryDto> getSubCategories(Long parentCategoryId);
}
