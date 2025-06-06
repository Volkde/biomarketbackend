package de.aittr.bio_marketplace.domain.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Objects;

@Entity
@Table(name = "category")
@Schema(description = "Class that describes a Category entity")
public class Category {

    // --- FIELDS ---

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Schema(description = "Category unique identifier", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Column(name = "name", nullable = false)
    @NotNull(message = "Category name cannot be null")
    @NotBlank(message = "Category name cannot be empty")
    @Size(min = 2, max = 255, message = "Category name must be between 2 and 255 characters")
    @Schema(description = "Category name", example = "Dairy Products")
    private String name;

    @Column(name = "description")
    @Size(max = 500, message = "Category description cannot exceed 500 characters")
    @Schema(description = "Category description", example = "Fresh dairy products from German farms")
    private String description;

    @Column(name = "parent_id")
    @Schema(description = "Parent category identifier, if this is a subcategory", example = "null")
    private Long parentId;

    // --- CONSTRUCTORS ---

    public Category() {
    }

    public Category(Long id, String name, String description, Long parentId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.parentId = parentId;
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

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    // --- Equals and hashcode ---

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return Objects.equals(id, category.id) &&
                Objects.equals(name, category.name) &&
                Objects.equals(description, category.description) &&
                Objects.equals(parentId, category.parentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, parentId);
    }
}