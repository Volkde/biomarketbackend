package de.aittr.bio_marketplace.controller;

import de.aittr.bio_marketplace.controller.responses.CategoriesResponse;
import de.aittr.bio_marketplace.domain.dto.CategoryDto;
import de.aittr.bio_marketplace.service.interfaces.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/categories")
@Tag(name = "Category controller", description = "Controller for operations with Categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Operation(
            summary = "Get all categories",
            description = "Returns a list of all categories in the database"
    )
    @GetMapping
    public CategoriesResponse getAllCategories() {
        List<CategoryDto> categories = categoryService.getAllCategories();
        return new CategoriesResponse(categories);
    }
}