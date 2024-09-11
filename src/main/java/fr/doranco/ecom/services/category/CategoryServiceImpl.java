package fr.doranco.ecom.services.category;

import fr.doranco.ecom.dto.CategoryDto;
import fr.doranco.ecom.dto.SubCategoryDto;
import fr.doranco.ecom.entities.Category;
import fr.doranco.ecom.exceptions.CategoryException;
import fr.doranco.ecom.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    private CategoryDto convertToDto(Category category) {
        CategoryDto dto = new CategoryDto();
        dto.setId(category.getId());
        dto.setName(category.getName());
        dto.setDescription(category.getDescription());
        dto.setParentCategoryId(category.getParentCategory() != null ? category.getParentCategory().getId() : null);

        List<SubCategoryDto> subCategories = category.getSubCategories().stream()
                .map(subCategory -> {
                    SubCategoryDto subDto = new SubCategoryDto();
                    subDto.setId(subCategory.getId());
                    subDto.setName(subCategory.getName());
                    return subDto;
                }).collect(Collectors.toList());

        dto.setSubCategories(subCategories);
        return dto;
    }

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category category = new Category();
        category.setName(categoryDto.getName());
        category.setDescription(categoryDto.getDescription());

        if (categoryDto.getParentCategoryId() != null) {
            Category parentCategory = categoryRepository.findById(categoryDto.getParentCategoryId())
                    .orElseThrow(() -> new CategoryException("Parent category not found"));
            category.setParentCategory(parentCategory);
        }

        return convertToDto(categoryRepository.save(category));
    }

    @Override
    public CategoryDto updateCategory(Long id, CategoryDto updatedCategoryDto) {
        Category existingCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryException("Category not found"));

        existingCategory.setName(updatedCategoryDto.getName());
        existingCategory.setDescription(updatedCategoryDto.getDescription());

        if (updatedCategoryDto.getParentCategoryId() != null) {
            Category parentCategory = categoryRepository.findById(updatedCategoryDto.getParentCategoryId())
                    .orElseThrow(() -> new CategoryException("Parent category not found"));
            existingCategory.setParentCategory(parentCategory);
        }

        return convertToDto(categoryRepository.save(existingCategory));
    }

    @Override
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public CategoryDto getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryException("Category not found"));
        return convertToDto(category);
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDto getCategoryByName(String name) {
        Category category = categoryRepository.findByName(name);
        return convertToDto(category);
    }

    @Override
    public List<CategoryDto> getSubCategories(Long parentCategoryId) {
        Category parentCategory = categoryRepository.findById(parentCategoryId)
                .orElseThrow(() -> new CategoryException("Parent category not found"));
        return parentCategory.getSubCategories().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
}