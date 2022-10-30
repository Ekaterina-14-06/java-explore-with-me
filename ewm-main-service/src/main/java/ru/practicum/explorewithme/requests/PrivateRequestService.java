package ru.practicum.explorewithme.requests;

import java.util.List;

public interface PrivateRequestService {

    List<ParticipationRequestDto> getRequests(long userId);

    ParticipationRequestDto postRequest(long userId, long eventId);

    ParticipationRequestDto cancelRequest(long userId, long requestId);
}
