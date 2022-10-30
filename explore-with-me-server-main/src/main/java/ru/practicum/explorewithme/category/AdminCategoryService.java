package ru.practicum.explorewithme.category;

import ru.practicum.explorewithme.category.CategoryDto;

public interface AdminCategoryService {

    CategoryDto patchCategory(CategoryDto categoryDto);

    CategoryDto postCategory(CategoryDto categoryDto);

    void deleteCategory(long catId);
}
