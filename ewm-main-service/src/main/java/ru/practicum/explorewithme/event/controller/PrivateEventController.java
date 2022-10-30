package ru.practicum.explorewithme.event.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.expression.AccessException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.event.dto.EventFullDto;
import ru.practicum.explorewithme.event.dto.EventShortDto;
import ru.practicum.explorewithme.event.dto.NewEventDto;
import ru.practicum.explorewithme.event.dto.UpdateEventRequest;
import ru.practicum.explorewithme.event.service.PrivateEventService;
import ru.practicum.explorewithme.requests.ParticipationRequestDto;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users/{userId}/events")
@RequiredArgsConstructor
@Validated
public class PrivateEventController {

    private final PrivateEventService privateEventService;

    @GetMapping
    public List<EventShortDto> getEvents(
            @PathVariable long userId,
            @PositiveOrZero @RequestParam int from,
            @Positive @RequestParam int size
    ) {
        return privateEventService.getEvents(userId, from, size);
    }

    @PatchMapping
    public EventFullDto patchEvent(
            @PathVariable long userId,
            @RequestBody UpdateEventRequest updateEventRequest
    ) {
        return privateEventService.patchEvent(userId, updateEventRequest);
    }

    @PostMapping
    public EventFullDto postEvent(
            @PathVariable long userId,
            @RequestBody @Valid NewEventDto newEventDto
    ) {
        log.info("User with id={}, post event {}", userId, newEventDto);
        if (newEventDto.getEventDate().isBefore(LocalDateTime.now().plusHours(2))) {
            throw new IllegalArgumentException(newEventDto.getEventDate().toString());
        }
        return privateEventService.postEvent(userId, newEventDto);
    }

    @GetMapping("/{eventId}")
    public EventFullDto getEvent(
            @PathVariable long userId,
            @PathVariable long eventId
    ) {
        log.info("Get event user with id={}, event {}", userId, eventId);
        return privateEventService.getEvent(userId, eventId);
    }

    @PatchMapping("/{eventId}")
    public EventFullDto patchEvent(
            @PathVariable long userId,
            @PathVariable long eventId
    ) throws AccessException {
        log.info("Patch event user id={}, event id={}", userId, eventId);
        return privateEventService.patchEvent(userId, eventId);
    }

    @GetMapping("/{eventId}/requests")
    public List<ParticipationRequestDto> getEventRequests(
            @PathVariable long userId,
            @PathVariable long eventId
    ) {
        log.info("Get user id={} event id={} requests", userId, eventId);
        return privateEventService.getEventRequests(userId, eventId);
    }

    @PatchMapping("/{eventId}/requests/{reqId}/confirm")
    public ParticipationRequestDto confirmRequest(
            @PathVariable long userId,
            @PathVariable long eventId,
            @PathVariable long reqId
    ) throws AccessException {
        log.info("Confirm user id={}, event id={}, request id={}", userId, eventId, reqId);
        return privateEventService.confirmRequest(userId, eventId, reqId);
    }

    @PatchMapping("/{eventId}/requests/{reqId}/reject")
    public ParticipationRequestDto rejectRequest(
            @PathVariable long userId,
            @PathVariable long eventId,
            @PathVariable long reqId
    ) throws AccessException {
        log.info("Reject user id={}, event id={}, request id={}", userId, eventId, reqId);
        return privateEventService.rejectRequest(userId, eventId, reqId);
    }
}
