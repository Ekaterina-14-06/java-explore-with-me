package ru.practicum.explorewithme.category.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.category.dto.CategoryDto;
import ru.practicum.explorewithme.category.service.PublicCategoriesService;

import java.util.List;


@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class PublicCategoriesController {

    private final PublicCategoriesService publicCategoriesService;

    @GetMapping
    public List<CategoryDto> getCategories(
            @RequestParam(required = false, defaultValue = "0") int from,
            @RequestParam(required = false, defaultValue = "10") int size
    ) {
        return publicCategoriesService.getCategories(from, size);
    }

    @GetMapping("/{catId}")
    public CategoryDto getCategory(@PathVariable long catId) {
        return publicCategoriesService.getCategory(catId);
    }
}
