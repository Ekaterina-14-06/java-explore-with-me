package ru.practicum.explorewithme.compilation.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import ru.practicum.explorewithme.compilation.model.Compilation;

import java.util.List;

@Component
public interface CompilationsRepository extends JpaRepository<Compilation, Long> {

    Page<Compilation> findAll(Pageable pageable);

    List<Compilation> findAllByPinned(boolean pinned, Pageable pageable);
}
