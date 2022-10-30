package ru.practicum.explorewithme.requests;

import org.springframework.stereotype.Component;

@Component
public class RequestMapper {

    public ParticipationRequestDto toParticipationRequestDto(ParticipationRequest request) {
        return new ParticipationRequestDto(
                request.getCreated(),
                request.getEvent().getId(),
                request.getId(),
                request.getRequester().getId(),
                request.getStatus().name()
        );
    }
}
