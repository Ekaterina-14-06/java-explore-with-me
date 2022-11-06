package ru.practicum.explorewithme.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explorewithme.dto.CategoryDto;
import ru.practicum.explorewithme.entity.Category;
import ru.practicum.explorewithme.mapper.CategoryMapper;
import ru.practicum.explorewithme.repository.CategoryRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminCategoryServiceImpl implements AdminCategoryService {

    private final CategoryRepository categoryRepository;

    private final CategoryMapper categoryMapper;


    @Override
    @Transactional
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
    @Transactional
    public CategoryDto postCategory(CategoryDto categoryDto) {
        Category savedCategory = categoryRepository.save(categoryMapper.toCategoryModel(categoryDto));
        return categoryMapper.toCategoryDto(savedCategory);
    }

    @Override
    @Transactional
    public void deleteCategory(long catId) {
        categoryRepository.deleteById(catId);
    }
}
