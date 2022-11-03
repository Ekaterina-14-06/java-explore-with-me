package ru.practicum.explorewithme.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explorewithme.dto.ParticipationRequestDto;
import ru.practicum.explorewithme.entity.Event;
import ru.practicum.explorewithme.entity.ParticipationRequest;
import ru.practicum.explorewithme.mapper.RequestMapper;
import ru.practicum.explorewithme.repository.EventRepository;
import ru.practicum.explorewithme.repository.RequestRepository;
import ru.practicum.explorewithme.entity.enums.RequestStatus;
import ru.practicum.explorewithme.entity.User;
import ru.practicum.explorewithme.repository.UserRepository;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PrivateRequestServiceImpl implements PrivateRequestService {

    private final RequestRepository requestRepository;

    private final EventRepository eventRepository;

    private final UserRepository userRepository;

    private final RequestMapper requestMapper;

    @Override
    public List<ParticipationRequestDto> getRequests(long userId) {
        List<ParticipationRequest> foundRequests = requestRepository.findAllByRequesterId(userId);
        return foundRequests.stream()
                .map(requestMapper::toParticipationRequestDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ParticipationRequestDto postRequest(long userId, long eventId) {
        User requester = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("loh"));
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new RuntimeException("loh"));
        if (event.getConfirmedRequests().size() == event.getParticipantLimit() && event.getParticipantLimit() != 0) {
            throw new IllegalArgumentException("Participant limit is full");
        }

        RequestStatus status;
        if (event.isRequestModeration()) {
            status = RequestStatus.PENDING;
        } else {
            status = RequestStatus.CONFIRMED;
        }

        ParticipationRequest request = new ParticipationRequest(
                LocalDateTime.now(),
                event,
                0,
                requester,
                status
        );

        return requestMapper.toParticipationRequestDto(requestRepository.save(request));
    }

    @Override
    @Transactional
    public ParticipationRequestDto cancelRequest(long userId, long requestId) {
        ParticipationRequest request = requestRepository.findByRequesterIdAndId(userId, requestId)
                .orElseThrow(() -> new EntityNotFoundException("Unable too find Request id " + requestId));
        request.setStatus(RequestStatus.CANCELED);
        ParticipationRequest cancelledRequest = requestRepository.save(request);
        return requestMapper.toParticipationRequestDto(cancelledRequest);
    }
}
