package ru.practicum.explorewithme.category;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.explorewithme.category.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PublicCategoryServiceImpl implements PublicCategoriesService {

    private final CategoryRepository publicCategoryRepository;

    private final CategoryMapper categoryMapper;

    @Override
    public List<CategoryDto> getCategories(int from, int size) {
        PageRequest pageRequest = PageRequest.of(from / size, size, Sort.by("id"));
        List<Category> foundCategories = publicCategoryRepository.findAll(pageRequest).getContent();
        return foundCategories.stream()
                .map(categoryMapper::toCategoryDto)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDto findCategoryById(long id) {
        Optional<Category> foundCategory = publicCategoryRepository.findById(id);
        return categoryMapper.toCategoryDto(foundCategory
                .orElseThrow(() -> new EntityNotFoundException("Unable to find Category id " + id)));
    }
}
