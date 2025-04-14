package de.aittr.bio_marketplace.service.interfaces;

import de.aittr.bio_marketplace.domain.dto.CategoryDto;

import java.util.List;

public interface CategoryService {

    //  --- Read ---
    List<CategoryDto> getAllCategories();
    CategoryDto getById(Long id);
}