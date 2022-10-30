package ru.practicum.explorewithme.category;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.explorewithme.category.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
