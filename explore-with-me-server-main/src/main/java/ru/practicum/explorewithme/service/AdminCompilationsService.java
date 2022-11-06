package ru.practicum.explorewithme.service;

import ru.practicum.explorewithme.dto.CompilationDto;
import ru.practicum.explorewithme.dto.AddingCompilationDto;

public interface AdminCompilationsService {

    CompilationDto postCompilation(AddingCompilationDto addingCompilationDto);

    void deleteCompilation(long compId);

    void deleteEventFromCompilation(long compId, long eventId);

    void addEventToCompilation(long compId, long eventId);

    void removeCompilationPin(long compId);

    void addCompilationPin(long compId);
}
