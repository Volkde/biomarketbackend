package de.aittr.bio_marketplace.controller.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;

@Schema(description = "Request body for updating an existing category")
public class UpdateCategoryRequest {

    @Size(min = 2, max = 255, message = "Category name must be between 2 and 255 characters")
    @Schema(description = "Name of the category (optional)", example = "Beverages")
    private String name;

    @Size(max = 500, message = "Category description cannot exceed 500 characters")
    @Schema(description = "Description of the category (optional)", example = "Various types of beverages")
    private String description;

    public UpdateCategoryRequest() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}