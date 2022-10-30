package ru.practicum.explorewithme.compilation.service;

import ru.practicum.explorewithme.compilation.dto.CompilationDto;

import java.util.List;

public interface PublicCompilationsService {
    List<CompilationDto> getCompilations(Boolean pinned, int from, int size);

    CompilationDto getCompilation(long compId);
}
