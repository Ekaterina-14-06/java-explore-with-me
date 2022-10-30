package ru.practicum.explorewithme.compilation.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.explorewithme.compilation.dto.CompilationDto;
import ru.practicum.explorewithme.compilation.dto.AddingCompilationDto;
import ru.practicum.explorewithme.compilation.model.Compilation;
import ru.practicum.explorewithme.event.mapper.EventMapper;
import ru.practicum.explorewithme.event.model.Event;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CompilationMapper {

    private final EventMapper eventMapper;

    public CompilationDto toCompilationDto(Compilation compilation) {
        return new CompilationDto(
                compilation.getEvents().stream()
                        .map(eventMapper::toEventShortDto)
                        .collect(Collectors.toList()),
                compilation.getId(),
                compilation.isPinned(),
                compilation.getTitle()
        );
    }

    public Compilation toCompilationModel(AddingCompilationDto addingCompilationDto, List<Event> events) {
        return new Compilation(
                0,
                events,
                addingCompilationDto.isPinned(),
                addingCompilationDto.getTitle()
        );
    }
}
