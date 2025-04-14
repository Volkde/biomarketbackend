package de.aittr.bio_marketplace.service.interfaces;

import de.aittr.bio_marketplace.domain.dto.CategoryDto;

import java.util.List;

public interface CategoryService {

    // --- Create ---
    CategoryDto save(CategoryDto categoryDto);

    //  --- Read ---
    List<CategoryDto> getAllCategories();
    CategoryDto getById(Long id);

    //  --- Update ---
    CategoryDto update(Long id, CategoryDto categoryDto);

    //  --- Delete ---
    CategoryDto deleteById(Long id);

}