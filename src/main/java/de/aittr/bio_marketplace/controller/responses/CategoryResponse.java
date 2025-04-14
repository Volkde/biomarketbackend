package de.aittr.bio_marketplace.controller.responses;

import de.aittr.bio_marketplace.domain.dto.CategoryDto;

public class CategoryResponse {

    private CategoryDto category;

    public CategoryResponse(CategoryDto category) {
        this.category = category;
    }

    public CategoryDto getCategory() {
        return category;
    }

    public void setCategory(CategoryDto category) {
        this.category = category;
    }
}