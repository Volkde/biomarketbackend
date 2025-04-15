package de.aittr.bio_marketplace.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Objects;

@Schema(description = "Class that describes Category DTO")
public class CategoryDto {

    // --- FIELDS ---

    @Schema(description = "Category unique identifier", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @NotNull(message = "Category name cannot be null")
    @NotBlank(message = "Category name cannot be empty")
    @Size(min = 2, max = 255, message = "Category name must be between 2 and 255 characters")
    @Schema(description = "Category name", example = "Dairy Products")
    private String name;

    @Size(max = 500, message = "Category description cannot exceed 500 characters")
    @Schema(description = "Category description", example = "Fresh dairy products from German farms")
    private String description;

    // --- CONSTRUCTORS ---

    public CategoryDto() {
    }

    public CategoryDto(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    // --- METHODS ---

    // --- Getters and setters ---

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    // --- Equals and hashcode ---

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CategoryDto that = (CategoryDto) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description);
    }
}