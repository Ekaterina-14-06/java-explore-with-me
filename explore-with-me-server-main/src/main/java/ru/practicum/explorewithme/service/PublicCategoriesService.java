package ru.practicum.explorewithme.service;

import ru.practicum.explorewithme.dto.CategoryDto;

import java.util.List;

public interface PublicCategoriesService {

    List<CategoryDto> getCategories(int from, int size);

    CategoryDto findCategoryById(long id);
}
