package fr.doranco.ecom.services.category;

import fr.doranco.ecom.entities.Category;
import fr.doranco.ecom.exceptions.CategoryException;
import fr.doranco.ecom.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements  CategoryService{

    @Autowired
    private final CategoryRepository categoryRepository;

    @Override
    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Category updateCategory(Long id, Category updatedCategory) {

        Optional<Category> categoryOptional = categoryRepository.findById(id);

        if (categoryOptional.isPresent()) {
            Category existingCategory = categoryOptional.get();
            existingCategory.setName(updatedCategory.getName());
            existingCategory.setDescription(updatedCategory.getDescription());
            existingCategory.setParentCategory(updatedCategory.getParentCategory());
            existingCategory.setSubCategories(updatedCategory.getSubCategories());

            return categoryRepository.save(existingCategory);
        } else {
            throw new CategoryException("Category not found with id: " + id);
        }
    }

    @Override
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public Optional<Category> getCategoryById(Long id) {
        return categoryRepository.findById(id);
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category getCategoryByName(String name) {
        return categoryRepository.findByName(name);
    }

    @Override
    public List<Category> getSubCategories(Long parentCategoryId) {
        Optional<Category> parentCategory = categoryRepository.findById(parentCategoryId);
        if (parentCategory.isPresent()){
            return parentCategory.get().getSubCategories();
        } else {
            throw new CategoryException("Parent category not found with id: " + parentCategoryId);
        }
    }
}
