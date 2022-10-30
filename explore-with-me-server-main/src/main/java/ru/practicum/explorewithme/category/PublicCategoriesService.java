package ru.practicum.explorewithme.category;

import ru.practicum.explorewithme.category.CategoryDto;

import java.util.List;

public interface PublicCategoriesService {

    List<CategoryDto> getCategories(int from, int size);

    CategoryDto findCategoryById(long id);
}
