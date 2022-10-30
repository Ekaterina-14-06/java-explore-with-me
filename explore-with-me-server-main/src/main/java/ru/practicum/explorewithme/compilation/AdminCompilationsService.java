package ru.practicum.explorewithme.compilation;

import ru.practicum.explorewithme.compilation.CompilationDto;
import ru.practicum.explorewithme.compilation.AddingCompilationDto;

public interface AdminCompilationsService {

    CompilationDto postCompilation(AddingCompilationDto addingCompilationDto);

    void deleteCompilation(long compId);

    void deleteEventFromCompilation(long compId, long eventId);

    void addEventToCompilation(long compId, long eventId);

    void removeCompilationPin(long compId);

    void addCompilationPin(long compId);
}
