package ru.practicum.explorewithme.category.mapper;

import org.springframework.stereotype.Component;
import ru.practicum.explorewithme.category.dto.CategoryDto;
import ru.practicum.explorewithme.category.model.Category;

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
