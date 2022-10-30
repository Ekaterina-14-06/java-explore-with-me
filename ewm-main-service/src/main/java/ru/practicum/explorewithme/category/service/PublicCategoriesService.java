package ru.practicum.explorewithme.category.service;

import ru.practicum.explorewithme.category.dto.CategoryDto;

import java.util.List;

public interface PublicCategoriesService {

    List<CategoryDto> getCategories(int from, int size);

    CategoryDto getCategory(long id);
}
