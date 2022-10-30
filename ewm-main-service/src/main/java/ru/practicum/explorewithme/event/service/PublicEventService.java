package ru.practicum.explorewithme.event.service;

import ru.practicum.explorewithme.event.dto.EventFullDto;
import ru.practicum.explorewithme.event.dto.EventShortDto;
import ru.practicum.explorewithme.event.EventSort;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

public interface PublicEventService {

    List<EventShortDto> getEvents(
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
    );

    EventFullDto getEvent(HttpServletRequest request, long id);
}
