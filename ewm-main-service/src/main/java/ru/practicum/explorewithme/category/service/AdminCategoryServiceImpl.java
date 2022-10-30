package ru.practicum.explorewithme.category.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.explorewithme.category.dto.CategoryDto;
import ru.practicum.explorewithme.category.mapper.CategoryMapper;
import ru.practicum.explorewithme.category.model.Category;
import ru.practicum.explorewithme.category.repository.CategoryRepository;

@Service
@RequiredArgsConstructor
public class AdminCategoryServiceImpl implements AdminCategoryService {

    private final CategoryRepository categoryRepository;

    private final CategoryMapper categoryMapper;


    @Override
    public CategoryDto patchCategory(CategoryDto categoryDto) {
        Category savedCategory = categoryRepository.getReferenceById(categoryDto.getId());
        if (categoryDto.getName() != null) {
            savedCategory.setName(categoryDto.getName());
        }
        return categoryMapper.toCategoryDto(
                categoryRepository.save(savedCategory)
        );
    }

    @Override
    public CategoryDto postCategory(CategoryDto categoryDto) {
        Category savedCategory = categoryRepository.save(categoryMapper.toCategoryModel(categoryDto));
        return categoryMapper.toCategoryDto(savedCategory);
    }

    @Override
    public void deleteCategory(long catId) {
        categoryRepository.deleteById(catId);
    }
}
