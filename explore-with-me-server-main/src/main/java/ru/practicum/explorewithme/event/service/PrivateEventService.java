package ru.practicum.explorewithme.event.service;

import org.springframework.expression.AccessException;
import ru.practicum.explorewithme.event.dto.EventFullDto;
import ru.practicum.explorewithme.event.dto.EventShortDto;
import ru.practicum.explorewithme.event.dto.NewEventDto;
import ru.practicum.explorewithme.event.dto.UpdateEventRequest;
import ru.practicum.explorewithme.requests.ParticipationRequestDto;

import java.util.List;

public interface PrivateEventService {

    List<EventShortDto> getEvents(long userId, int from, int size);

    EventFullDto patchEvent(long userId, UpdateEventRequest updateEventRequest);

    EventFullDto postEvent(long userId, NewEventDto newEventDto);

    EventFullDto getEvent(long userId, long eventId);

    EventFullDto patchEvent(long userId, long eventId) throws AccessException;

    List<ParticipationRequestDto> getEventRequests(long userId, long eventId);

    ParticipationRequestDto confirmRequest(long userId, long eventId, long reqId) throws AccessException;

    ParticipationRequestDto rejectRequest(long userId, long eventId, long reqId) throws AccessException;
}
