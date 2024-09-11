package fr.doranco.ecom.dto;

import lombok.Data;

import java.util.List;

@Data
public class CategoryDto {

    private Long id;
    private String name;
    private String description;
    private Long parentCategoryId; // Utilisez un ID pour la relation parent
    private List<SubCategoryDto> subCategories;

}
