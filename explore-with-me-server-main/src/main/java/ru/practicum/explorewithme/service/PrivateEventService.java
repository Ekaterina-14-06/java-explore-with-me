package ru.practicum.explorewithme.service;

import org.springframework.expression.AccessException;
import ru.practicum.explorewithme.dto.EventFullDto;
import ru.practicum.explorewithme.dto.EventShortDto;
import ru.practicum.explorewithme.dto.NewEventDto;
import ru.practicum.explorewithme.dto.UpdateEventRequest;
import ru.practicum.explorewithme.dto.ParticipationRequestDto;
import ru.practicum.explorewithme.entity.enums.LikeType;

import java.nio.file.AccessDeniedException;
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

    void addLike(Long userId, Long eventId, LikeType likeType) throws AccessException;

    void removeLike(Long userId, Long eventId, LikeType likeType) throws AccessException;
}
