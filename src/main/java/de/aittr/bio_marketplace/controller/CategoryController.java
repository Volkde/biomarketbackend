package de.aittr.bio_marketplace.controller;

import de.aittr.bio_marketplace.controller.requests.AddCategoryRequest;
import de.aittr.bio_marketplace.controller.requests.UpdateCategoryRequest;
import de.aittr.bio_marketplace.controller.responses.CategoriesResponse;
import de.aittr.bio_marketplace.controller.responses.CategoryResponse;
import de.aittr.bio_marketplace.domain.dto.CategoryDto;
import de.aittr.bio_marketplace.service.interfaces.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
@Tag(name = "Category controller", description = "Controller for operations with Categories")
public class CategoryController {

    // --- FIELDS ---

    private final CategoryService categoryService;

    // --- CONSTRUCTOR ---

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    // --- METHODS ---

    // --- Create ---

    @Operation(
            summary = "Save category",
            description = "Saves a new category with the given parameters, wrapped in a 'category' object"
    )
    @PostMapping
    public CategoryResponse saveCategory(
            @RequestBody
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Instance of a Category to save")
            AddCategoryRequest request
    ) {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setName(request.getName());
        categoryDto.setDescription(request.getDescription());
        CategoryDto savedCategory = categoryService.save(categoryDto);
        return new CategoryResponse(savedCategory);
    }

    // --- Read ---

    @Operation(
            summary = "Get all categories",
            description = "Returns a list of all categories in the database"
    )
    @GetMapping
    public CategoriesResponse getAllCategories() {
        List<CategoryDto> categories = categoryService.getAllCategories();
        return new CategoriesResponse(categories);
    }

    @Operation(
            summary = "Get category by ID",
            description = "Returns a category from the database by its ID, wrapped in a 'category' object"
    )
    @GetMapping("/{categoryId}")
    public CategoryResponse getCategoryById(
            @PathVariable
            @Parameter(description = "Category unique identifier")
            Long categoryId
    ) {
        CategoryDto categoryDto = categoryService.getById(categoryId);
        return new CategoryResponse(categoryDto);
    }

    //  --- Update ---

    @Operation(
            summary = "Update category by ID (admin)",
            description = "Updates an existing category by its ID with the given parameters, wrapped in a 'category' object"
    )
    @PutMapping("/{categoryId}")
    public CategoryResponse updateCategoryById(
            @PathVariable
            @Parameter(description = "Category unique identifier")
            Long categoryId,
            @RequestBody
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Instance of a Category to update")
            UpdateCategoryRequest request
    ) {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setName(request.getName());
        categoryDto.setDescription(request.getDescription());
        CategoryDto updatedCategory = categoryService.update(categoryId, categoryDto);
        return new CategoryResponse(updatedCategory);
    }

}