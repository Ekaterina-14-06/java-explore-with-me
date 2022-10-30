package ru.practicum.explorewithme.event.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.explorewithme.category.repository.CategoryRepository;
import ru.practicum.explorewithme.event.dto.AdminUpdateEventRequest;
import ru.practicum.explorewithme.event.dto.EventFullDto;
import ru.practicum.explorewithme.event.EventStatus;
import ru.practicum.explorewithme.event.mapper.EventMapper;
import ru.practicum.explorewithme.event.model.Event;
import ru.practicum.explorewithme.event.repository.EventRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminEventServiceImpl implements AdminEventService {

    private final EventRepository eventRepository;

    private final CategoryRepository categoryRepository;

    private final EventMapper eventMapper;

    @Override
    public List<EventFullDto> getEvents(
            List<Long> users,
            List<EventStatus> states,
            List<Long> categories,
            LocalDateTime rangeStart,
            LocalDateTime rangeEnd,
            int from,
            int size
    ) {
        PageRequest pageRequest = PageRequest.of(from / size, size, Sort.by("eventDate").descending());
        if (rangeStart ==  null) {
            rangeStart = LocalDateTime.now();
        }
        if (rangeEnd == null) {
            rangeEnd = LocalDateTime.now().plusYears(100);
        }

        List<Event> events;
        if (users == null && states == null && categories == null) {
            events = eventRepository.findAll(rangeStart, rangeEnd, pageRequest);
        } else if (users != null && states == null && categories == null) {
            events = eventRepository.findAllByInitiators(users, rangeStart, rangeEnd, pageRequest);
        } else if (users == null && states != null && categories == null) {
            events = eventRepository.findAllByStates(states, rangeStart, rangeEnd, pageRequest);
        } else if (users == null && states == null && categories != null) {
            events = eventRepository.findAllByCategories(categories, rangeStart, rangeEnd, pageRequest);
        } else if (users != null && states != null && categories == null) {
            events = eventRepository.findAllByInitiatorsAndStates(users, states, rangeStart, rangeEnd, pageRequest);
        } else if (users != null && states == null && categories != null) {
            events = eventRepository.findAllByInitiatorsAndCategories(users, categories, rangeStart, rangeEnd, pageRequest);
        } else if (users == null && states != null && categories != null) {
            events = eventRepository.findAllByStatesAndCategories(states, categories, rangeStart, rangeEnd, pageRequest);
        } else {
            events = eventRepository.findAllByInitiatorsAndStatesAndCategories(users, states, categories, rangeStart, rangeEnd, pageRequest);
        }
        return events.stream()
                .map(eventMapper::toEventFullDto)
                .collect(Collectors.toList());
    }

    @Override
    public EventFullDto updateEvent(long eventId, AdminUpdateEventRequest adminUpdateEventRequest) {
        Event event = eventRepository.save(
                patch(eventRepository.getReferenceById(eventId), adminUpdateEventRequest)
        );
        return eventMapper.toEventFullDto(event);
    }

    @Override
    public EventFullDto publishEvent(long eventId) {
        validateEvent(eventId);
        Event event = eventRepository.getReferenceById(eventId);
        event.setState(EventStatus.PUBLISHED);
        event.setPublishedOn(LocalDateTime.now());
        return eventMapper.toEventFullDto(eventRepository.save(event));
    }

    @Override
    public EventFullDto rejectEvent(long eventId) {
        validateEvent(eventId);
        Event event = eventRepository.getReferenceById(eventId);
        event.setState(EventStatus.CANCELED);
        return eventMapper.toEventFullDto(eventRepository.save(event));
    }

    private void validateEvent(long eventId) {
        Event event = eventRepository.findById(eventId).orElseThrow();
        if (event.getEventDate().isBefore(LocalDateTime.now().plusHours(1))) {
            throw new RuntimeException();
        }
        if (!event.getState().equals(EventStatus.PENDING)) {
            throw new RuntimeException();
        }
    }

    private Event patch(Event event, AdminUpdateEventRequest update) {
        if (update.getAnnotation() != null) {
            event.setAnnotation(update.getAnnotation());
        }
        if (update.getCategory() != null) {
            event.setCategory(categoryRepository.findById(update.getCategory()).orElseThrow());
        }
        if (update.getDescription() != null) {
            event.setDescription(update.getDescription());
        }
        if (update.getEventDate() != null) {
            event.setEventDate(update.getEventDate());
        }
        if (update.getLocation() != null) {
            event.setLocation(update.getLocation());
        }
        if (update.getPaid() != null) {
            event.setPaid(update.getPaid());
        }
        if (update.getParticipantLimit() != null) {
            event.setParticipantLimit(update.getParticipantLimit());
        }
        if (update.getRequestModeration() != null) {
            event.setRequestModeration(update.getRequestModeration());
        }
        if (update.getTitle() != null) {
            event.setTitle(update.getTitle());
        }
        return event;
    }
}
