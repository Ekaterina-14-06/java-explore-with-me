package ru.practicum.explorewithme.compilation.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.explorewithme.compilation.dto.CompilationDto;
import ru.practicum.explorewithme.compilation.dto.AddingCompilationDto;
import ru.practicum.explorewithme.compilation.mapper.CompilationMapper;
import ru.practicum.explorewithme.compilation.model.Compilation;
import ru.practicum.explorewithme.compilation.repository.CompilationsRepository;
import ru.practicum.explorewithme.event.model.Event;
import ru.practicum.explorewithme.event.repository.EventRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminCompilationsServiceImpl implements AdminCompilationsService {

    private final CompilationsRepository compilationsRepository;

    private final EventRepository eventRepository;

    private final CompilationMapper compilationMapper;

    @Override
    public CompilationDto postCompilation(AddingCompilationDto addingCompilationDto) {
        List<Event> events = eventRepository.findAllById(addingCompilationDto.getEvents());
        Compilation compilation = compilationMapper.toCompilationModel(addingCompilationDto, events);
        return compilationMapper.toCompilationDto(compilationsRepository.save(compilation));
    }

    @Override
    public void deleteCompilation(long compId) {
        compilationsRepository.deleteById(compId);
    }

    @Override
    public void deleteEventFromCompilation(long compId, long eventId) {
        Compilation compilation = compilationsRepository.getReferenceById(compId);
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EntityNotFoundException("Unable to find Event id " + eventId));
        List<Event> events = compilation.getEvents();
        events.remove(event);
        compilationsRepository.save(compilation);
    }

    @Override
    public void addEventToCompilation(long compId, long eventId) {
        Compilation compilation = compilationsRepository.getReferenceById(compId);
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EntityNotFoundException("Unable to find Event id " + eventId));
        List<Event> events = compilation.getEvents();
        events.add(event);
        compilationsRepository.save(compilation);
    }

    @Override
    public void removeCompilationPin(long compId) {
        Compilation compilation = compilationsRepository.getReferenceById(compId);
        compilation.setPinned(false);
        compilationsRepository.save(compilation);
    }

    @Override
    public void addCompilationPin(long compId) {
        Compilation compilation = compilationsRepository.getReferenceById(compId);
        compilation.setPinned(true);
        compilationsRepository.save(compilation);
    }
}
