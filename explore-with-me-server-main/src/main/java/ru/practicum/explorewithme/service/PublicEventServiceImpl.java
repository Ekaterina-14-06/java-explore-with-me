package ru.practicum.explorewithme.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explorewithme.dto.EventFullDto;
import ru.practicum.explorewithme.dto.EventShortDto;
import ru.practicum.explorewithme.entity.enums.EventSort;
import ru.practicum.explorewithme.entity.enums.EventStatus;
import ru.practicum.explorewithme.mapper.EventMapper;
import ru.practicum.explorewithme.entity.Event;
import ru.practicum.explorewithme.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.explorewithme.config.StatsClient;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PublicEventServiceImpl implements PublicEventService {

    private final EventRepository eventRepository;

    private final StatsClient statsClient;

    private final EventMapper eventMapper;

    @Override
    public List<EventShortDto> getEvents(
            HttpServletRequest request,
            String text,
            List<Long> categories,
            Boolean paid,
            LocalDateTime rangeStart,
            LocalDateTime rangeEnd,
            boolean onlyAvailable,
            EventSort sort,
            int from,
            int size
    ) {
        String sortParam = "views";
        if (sort.equals(EventSort.EVENT_DATE)) {
            sortParam = "eventDate";
        }
        if (sort.equals(EventSort.RATE)) {
            sortParam = "rate";
        }
        PageRequest pageRequest = PageRequest.of(from / size, size, Sort.by(sortParam));
        if (text == null) {
            text = "";
        }
        if (rangeStart == null) {
            rangeStart = LocalDateTime.now();
        }
        if (rangeEnd == null) {
            rangeEnd = LocalDateTime.now().plusYears(100);
        }
        if (categories == null) {
            categories = List.of();
        }
        List<Event> events;
        if (categories.isEmpty() && paid == null && onlyAvailable) {
            events = eventRepository.findAllAvailable(text, rangeStart, rangeEnd, pageRequest);
        } else if (categories.isEmpty() && paid == null) {
            events = eventRepository.findAll(text, rangeStart, rangeEnd, pageRequest);
        } else if (categories.isEmpty() && onlyAvailable) {
            events = eventRepository.findAllAvailable(text, paid, rangeStart, rangeEnd, pageRequest);
        } else if (categories.isEmpty()) {
            events = eventRepository.findAll(text, paid, rangeStart, rangeEnd, pageRequest);
        } else if (paid == null && onlyAvailable) {
            events = eventRepository.findAllAvailable(text, categories, rangeStart, rangeEnd, pageRequest);
        } else if (paid == null) {
            events = eventRepository.findAll(text, categories, rangeStart, rangeEnd, pageRequest);
        } else if (onlyAvailable) {
            events = eventRepository.findAllAvailable(text, categories, paid, rangeStart, rangeEnd, pageRequest);
        } else {
            events = eventRepository.findAll(text, categories, paid, rangeStart, rangeEnd, pageRequest);
        }
        events.forEach(e -> e.setViews(statsClient.getViews(e.getId())));
        statsClient.hit(request);
        return events.stream().map(eventMapper::toEventShortDto)
                .collect(Collectors.toList());
    }

    @Override
    public EventFullDto getEvent(HttpServletRequest request, long eventId) {
        Event foundEvent = eventRepository.findByIdAndState(eventId, EventStatus.PUBLISHED)
                .orElseThrow(() -> new EntityNotFoundException("Unable to find Event id " + eventId));
        foundEvent.setViews(statsClient.getViews(eventId));
        statsClient.hit(request);
        return eventMapper.toEventFullDto(foundEvent);
    }

}
