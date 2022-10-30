package ru.practicum.explorewithme.category.service;

import ru.practicum.explorewithme.category.dto.CategoryDto;

public interface AdminCategoryService {

    CategoryDto patchCategory(CategoryDto categoryDto);

    CategoryDto postCategory(CategoryDto categoryDto);

    void deleteCategory(long catId);
}
