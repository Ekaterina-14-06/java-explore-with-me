package ru.practicum.explorewithme.category;

import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public CategoryDto toCategoryDto(Category category) {
        return new CategoryDto(
                category.getId(),
                category.getName()
        );
    }

    public Category toCategoryModel(CategoryDto categoryDto) {
        return new Category(
                categoryDto.getId(),
                categoryDto.getName()
        );
    }
}
