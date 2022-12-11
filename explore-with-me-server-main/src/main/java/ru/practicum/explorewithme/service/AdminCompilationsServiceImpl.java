package ru.practicum.explorewithme.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explorewithme.entity.Compilation;
import ru.practicum.explorewithme.dto.CompilationDto;
import ru.practicum.explorewithme.mapper.CompilationMapper;
import ru.practicum.explorewithme.repository.CompilationsRepository;
import ru.practicum.explorewithme.dto.AddingCompilationDto;
import ru.practicum.explorewithme.entity.Event;
import ru.practicum.explorewithme.repository.EventRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminCompilationsServiceImpl implements AdminCompilationsService {

    private final CompilationsRepository compilationsRepository;

    private final EventRepository eventRepository;

    private final CompilationMapper compilationMapper;

    @Override
    @Transactional
    public CompilationDto postCompilation(AddingCompilationDto addingCompilationDto) {
        List<Event> events = eventRepository.findAllById(addingCompilationDto.getEvents());
        Compilation compilation = compilationMapper.toCompilationModel(addingCompilationDto, events);
        return compilationMapper.toCompilationDto(compilationsRepository.save(compilation));
    }

    @Override
    @Transactional
    public void deleteCompilation(long compId) {
        compilationsRepository.deleteById(compId);
    }

    @Override
    @Transactional
    public void deleteEventFromCompilation(long compId, long eventId) {
        Compilation compilation = compilationsRepository.getReferenceById(compId);
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EntityNotFoundException("Unable to find Event id " + eventId));
        List<Event> events = compilation.getEvents();
        events.remove(event);
        compilationsRepository.save(compilation);
    }

    @Override
    @Transactional
    public void addEventToCompilation(long compId, long eventId) {
        Compilation compilation = compilationsRepository.getReferenceById(compId);
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EntityNotFoundException("Unable to find Event id " + eventId));
        List<Event> events = compilation.getEvents();
        events.add(event);
        compilationsRepository.save(compilation);
    }

    @Override
    @Transactional
    public void removeCompilationPin(long compId) {
        Compilation compilation = compilationsRepository.getReferenceById(compId);
        compilation.setPinned(false);
        compilationsRepository.save(compilation);
    }

    @Override
    @Transactional
    public void addCompilationPin(long compId) {
        Compilation compilation = compilationsRepository.getReferenceById(compId);
        compilation.setPinned(true);
        compilationsRepository.save(compilation);
    }
}
