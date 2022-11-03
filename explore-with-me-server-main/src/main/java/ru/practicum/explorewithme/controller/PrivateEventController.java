package ru.practicum.explorewithme.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.expression.AccessException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.dto.EventFullDto;
import ru.practicum.explorewithme.dto.EventShortDto;
import ru.practicum.explorewithme.dto.NewEventDto;
import ru.practicum.explorewithme.dto.UpdateEventRequest;
import ru.practicum.explorewithme.service.PrivateEventService;
import ru.practicum.explorewithme.dto.ParticipationRequestDto;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.List;


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
        return privateEventService.getEvent(userId, eventId);
    }

    @PatchMapping("/{eventId}")
    public EventFullDto patchEvent(
            @PathVariable long userId,
            @PathVariable long eventId
    ) throws AccessException {
        return privateEventService.patchEvent(userId, eventId);
    }

    @GetMapping("/{eventId}/requests")
    public List<ParticipationRequestDto> getEventRequests(
            @PathVariable long userId,
            @PathVariable long eventId
    ) {
        return privateEventService.getEventRequests(userId, eventId);
    }

    @PatchMapping("/{eventId}/requests/{reqId}/confirm")
    public ParticipationRequestDto confirmRequest(
            @PathVariable long userId,
            @PathVariable long eventId,
            @PathVariable long reqId
    ) throws AccessException {
        return privateEventService.confirmRequest(userId, eventId, reqId);
    }

    @PatchMapping("/{eventId}/requests/{reqId}/reject")
    public ParticipationRequestDto rejectRequest(
            @PathVariable long userId,
            @PathVariable long eventId,
            @PathVariable long reqId
    ) throws AccessException {
        return privateEventService.rejectRequest(userId, eventId, reqId);
    }
}
