package de.aittr.bio_marketplace.service;

import de.aittr.bio_marketplace.domain.dto.CategoryDto;
import de.aittr.bio_marketplace.domain.entity.Category;
import de.aittr.bio_marketplace.repository.CategoryRepository;
import de.aittr.bio_marketplace.service.interfaces.CategoryService;
import de.aittr.bio_marketplace.service.mapping.CategoryMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    //  --- Create ---

    @Override
    public CategoryDto save(CategoryDto categoryDto) {
        Category category = categoryMapper.mapDtoToEntity(categoryDto);
        Category savedCategory = categoryRepository.save(category);
        return categoryMapper.mapEntityToDto(savedCategory);
    }

    //  --- Read ---

    @Override
    public List<CategoryDto> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream()
                .map(categoryMapper::mapEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDto getById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category with id " + id + " not found"));
        return categoryMapper.mapEntityToDto(category);
    }

}