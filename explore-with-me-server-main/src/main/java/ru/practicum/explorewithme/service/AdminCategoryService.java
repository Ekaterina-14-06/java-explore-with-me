package ru.practicum.explorewithme.service;

import ru.practicum.explorewithme.dto.CategoryDto;

public interface AdminCategoryService {

    CategoryDto patchCategory(CategoryDto categoryDto);

    CategoryDto postCategory(CategoryDto categoryDto);

    void deleteCategory(long catId);
}
