package ru.practicum.explorewithme.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.expression.AccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explorewithme.entity.Category;
import ru.practicum.explorewithme.repository.CategoryRepository;
import ru.practicum.explorewithme.dto.EventFullDto;
import ru.practicum.explorewithme.dto.EventShortDto;
import ru.practicum.explorewithme.dto.NewEventDto;
import ru.practicum.explorewithme.dto.UpdateEventRequest;
import ru.practicum.explorewithme.entity.enums.EventStatus;
import ru.practicum.explorewithme.mapper.EventMapper;
import ru.practicum.explorewithme.entity.Event;
import ru.practicum.explorewithme.entity.Location;
import ru.practicum.explorewithme.repository.EventRepository;
import ru.practicum.explorewithme.repository.LocationRepository;
import ru.practicum.explorewithme.dto.ParticipationRequestDto;
import ru.practicum.explorewithme.entity.enums.RequestStatus;
import ru.practicum.explorewithme.mapper.RequestMapper;
import ru.practicum.explorewithme.entity.ParticipationRequest;
import ru.practicum.explorewithme.repository.RequestRepository;
import ru.practicum.explorewithme.entity.User;
import ru.practicum.explorewithme.repository.UserRepository;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PrivateEventServiceImpl implements PrivateEventService {

    private final EventRepository eventRepository;

    private final CategoryRepository categoryRepository;

    private final UserRepository userRepository;

    private final RequestRepository requestRepository;

    private final LocationRepository locationRepository;

    private final EventMapper eventMapper;

    private final RequestMapper requestMapper;

    @Override
    public List<EventShortDto> getEvents(long userId, int from, int size) {
        PageRequest pageRequest = PageRequest.of(from / size, size, Sort.by("eventDate"));
        List<Event> foundEvents = eventRepository.findAllByInitiatorId(userId, pageRequest);
        return foundEvents.stream()
                .map(eventMapper::toEventShortDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public EventFullDto patchEvent(long userId, UpdateEventRequest updateEventRequest) {
        Optional<Event> foundEvent = eventRepository.findById(updateEventRequest.getEventId());
        if (foundEvent.isEmpty()) {
            throw new EntityNotFoundException("Unable to find Event id " + updateEventRequest.getEventId());
        }
        if (foundEvent.get().getState().equals(EventStatus.PUBLISHED)) {
            throw new IllegalArgumentException("State must be on of [PENDING, CANCELED]");
        }
        if (foundEvent.get().getEventDate().isBefore(LocalDateTime.now().plusHours(2))) {
            throw new IllegalArgumentException("Event date must be after " + LocalDateTime.now().plusHours(2));
        }

        Event updatedEvent = eventRepository.save(patch(updateEventRequest));
        return eventMapper.toEventFullDto(updatedEvent);
    }

    @Override
    @Transactional
    public EventFullDto postEvent(long userId, NewEventDto newEventDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Unable to find User id " + userId));
        Category category = categoryRepository.findById(newEventDto.getCategory())
                .orElseThrow(() -> new EntityNotFoundException("Unable to find Category id " + newEventDto.getCategory()));
        Location location = locationRepository.save(newEventDto.getLocation());
        Event savedEvent = eventRepository.save(eventMapper.toEventModel(newEventDto, category, user, location));
        return eventMapper.toEventFullDto(savedEvent);
    }

    @Override
    public EventFullDto getEvent(long userId, long eventId) {
        Optional<Event> foundEvent = eventRepository.findByIdAndInitiatorId(eventId, userId);
        return eventMapper.toEventFullDto(
                foundEvent
                .orElseThrow(() -> new EntityNotFoundException("Unable to find Event id " + eventId))
        );
    }

    @Override
    @Transactional
    public EventFullDto patchEvent(long userId, long eventId) throws AccessException {
        Event event = eventRepository.getReferenceById(eventId);

        if (!event.getState().equals(EventStatus.PENDING)) {
            throw new IllegalArgumentException("State must be PENDING");
        }
        if (event.getInitiator().getId() != userId) {
            throw new AccessException("User id " + userId + " not initiator");
        }

        event.setState(EventStatus.CANCELED);
        return eventMapper.toEventFullDto(eventRepository.save(event));
    }

    @Override
    public List<ParticipationRequestDto> getEventRequests(long userId, long eventId) {
        List<ParticipationRequest> foundRequests = requestRepository.findAllByEventId(eventId);
        return foundRequests.stream()
                .map(requestMapper::toParticipationRequestDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ParticipationRequestDto confirmRequest(long userId, long eventId, long reqId) throws AccessException {
        ParticipationRequest request = requestRepository.findById(reqId)
                .orElseThrow(() -> new EntityNotFoundException("Unable to find Request id " + reqId));
        if (request.getEvent().getId() != eventId) {
            throw new EntityNotFoundException("Unable to find Request id " + reqId);
        }

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EntityNotFoundException("Unable to find Event id " + eventId));
        if (event.getInitiator().getId() != userId) {
            throw new AccessException("User id " + userId + " not initiator");
        }
        if (event.getConfirmedRequests().size() == event.getParticipantLimit()) {
            throw new AccessException("Participant limit is full");
        }

        ParticipationRequest confirmingRequest = requestRepository.getReferenceById(reqId);
        confirmingRequest.setStatus(RequestStatus.CONFIRMED);
        ParticipationRequest confirmedRequest = requestRepository.save(confirmingRequest);

        Event updatedEvent = eventRepository.getReferenceById(eventId);
        List<ParticipationRequest> confirmedRequests = updatedEvent.getConfirmedRequests();
        confirmedRequests.add(confirmedRequest);
        updatedEvent.setConfirmedRequests(confirmedRequests);
        eventRepository.save(updatedEvent);

        return requestMapper.toParticipationRequestDto(confirmedRequest);
    }

    @Override
    @Transactional
    public ParticipationRequestDto rejectRequest(long userId, long eventId, long reqId) throws AccessException {
        ParticipationRequest request = requestRepository.findById(reqId)
                .orElseThrow(() -> new EntityNotFoundException("Unable to find Request id " + reqId));
        if (request.getEvent().getId() != eventId) {
            throw new EntityNotFoundException("Unable to find Request id " + reqId);
        }

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EntityNotFoundException("Unable to find Event id " + eventId));
        if (event.getInitiator().getId() != userId) {
            throw new AccessException("User id " + userId + " not initiator");
        }

        ParticipationRequest rejectingRequest = requestRepository.getReferenceById(reqId);
        rejectingRequest.setStatus(RequestStatus.REJECTED);
        ParticipationRequest confirmedRequest = requestRepository.save(rejectingRequest);

        return requestMapper.toParticipationRequestDto(confirmedRequest);
    }

    private Event patch(UpdateEventRequest updateEventRequest) {
        Event savedEvent = eventRepository.getReferenceById(updateEventRequest.getEventId());
        if (updateEventRequest.getAnnotation() != null) {
            savedEvent.setAnnotation(updateEventRequest.getAnnotation());
        }
        if (updateEventRequest.getCategory() != null) {
            Optional<Category> patchCategory = categoryRepository.findById(updateEventRequest.getCategory());
            savedEvent.setCategory(
                    patchCategory
                    .orElseThrow(() -> new EntityNotFoundException("Unable to find Category id " + updateEventRequest.getCategory()))
            );
        }
        if (updateEventRequest.getDescription() != null) {
            savedEvent.setDescription(updateEventRequest.getDescription());
        }
        if (updateEventRequest.getEventDate() != null) {
            savedEvent.setEventDate(updateEventRequest.getEventDate());
        }
        if (updateEventRequest.getPaid() != null) {
            savedEvent.setPaid(updateEventRequest.getPaid());
        }
        if (updateEventRequest.getParticipantLimit() != null) {
            savedEvent.setParticipantLimit(updateEventRequest.getParticipantLimit());
        }
        if (updateEventRequest.getTitle() != null) {
            savedEvent.setTitle(updateEventRequest.getTitle());
        }
        if (savedEvent.getState().equals(EventStatus.CANCELED)) {
            savedEvent.setState(EventStatus.PENDING);
        }
        return savedEvent;
    }

}
