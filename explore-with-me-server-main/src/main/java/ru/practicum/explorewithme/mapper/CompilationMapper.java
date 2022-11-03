package ru.practicum.explorewithme.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.explorewithme.dto.AddingCompilationDto;
import ru.practicum.explorewithme.dto.CompilationDto;
import ru.practicum.explorewithme.entity.Compilation;
import ru.practicum.explorewithme.mapper.EventMapper;
import ru.practicum.explorewithme.entity.Event;

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
