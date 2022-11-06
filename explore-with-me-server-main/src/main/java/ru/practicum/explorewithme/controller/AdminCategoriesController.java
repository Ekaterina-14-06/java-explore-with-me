package ru.practicum.explorewithme.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.service.AdminCategoryService;
import ru.practicum.explorewithme.dto.CategoryDto;

import javax.validation.Valid;


@RestController
@RequestMapping("/admin/categories")
@RequiredArgsConstructor
@Validated
public class AdminCategoriesController {

    private final AdminCategoryService adminCategoryService;

    @PatchMapping
    public CategoryDto patchCategory(
            @RequestBody @Valid CategoryDto categoryDto
    ) {
        return adminCategoryService.patchCategory(categoryDto);
    }

    @PostMapping
    public CategoryDto postCategory(
            @RequestBody @Valid CategoryDto categoryDto
    ) {
        return adminCategoryService.postCategory(categoryDto);
    }

    @DeleteMapping("/{catId}")
    public void deleteCategory(
            @PathVariable long catId
    ) {
        adminCategoryService.deleteCategory(catId);
    }
}
