package de.aittr.bio_marketplace.controller.responses;

import de.aittr.bio_marketplace.domain.dto.CategoryDto;

import java.util.List;

public class CategoriesResponse {

    private List<CategoryDto> categories;

    public CategoriesResponse(List<CategoryDto> categories) {
        this.categories = categories;
    }

    public List<CategoryDto> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoryDto> categories) {
        this.categories = categories;
    }
}