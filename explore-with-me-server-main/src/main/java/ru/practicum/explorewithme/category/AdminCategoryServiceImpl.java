package ru.practicum.explorewithme.category;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.explorewithme.category.*;

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
