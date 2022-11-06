package ru.practicum.explorewithme.service;

import ru.practicum.explorewithme.dto.AdminUpdateEventRequest;
import ru.practicum.explorewithme.dto.EventFullDto;
import ru.practicum.explorewithme.entity.enums.EventStatus;

import java.time.LocalDateTime;
import java.util.List;

public interface AdminEventService {

    List<EventFullDto> getEvents(
            List<Long> users,
            List<EventStatus> states,
            List<Long> categories,
            LocalDateTime rangeStart,
            LocalDateTime rangeEnd,
            int from,
            int size
    );

    EventFullDto updateEvent(long eventId, AdminUpdateEventRequest adminUpdateEventRequest);

    EventFullDto publishEvent(long eventId);

    EventFullDto rejectEvent(long eventId);
}
