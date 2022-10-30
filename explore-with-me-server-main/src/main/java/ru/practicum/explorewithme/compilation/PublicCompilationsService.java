package ru.practicum.explorewithme.compilation;

import ru.practicum.explorewithme.compilation.CompilationDto;

import java.util.List;

public interface PublicCompilationsService {
    List<CompilationDto> getCompilations(Boolean pinned, int from, int size);

    CompilationDto getCompilation(long compId);
}
