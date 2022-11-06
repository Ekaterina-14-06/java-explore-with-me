package ru.practicum.explorewithme.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.dto.ParticipationRequestDto;
import ru.practicum.explorewithme.service.PrivateRequestService;

import java.util.List;


@RestController
@RequestMapping("/users/{userId}/requests")
@RequiredArgsConstructor
public class PrivateRequestController {

    private final PrivateRequestService privateRequestService;

    @GetMapping
    public List<ParticipationRequestDto> getRequests(
            @PathVariable long userId
    ) {
        return privateRequestService.getRequests(userId);
    }

    @PostMapping
    public ParticipationRequestDto postRequest(
            @PathVariable long userId,
            @RequestParam long eventId
    ) {
        return privateRequestService.postRequest(userId, eventId);
    }

    @PatchMapping("/{requestId}/cancel")
    public ParticipationRequestDto cancelRequest(
            @PathVariable long userId,
            @PathVariable long requestId
    ) {
        return privateRequestService.cancelRequest(userId, requestId);
    }
}
