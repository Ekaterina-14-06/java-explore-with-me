package ru.practicum.explorewithme.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.expression.AccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explorewithme.entity.*;
import ru.practicum.explorewithme.entity.enums.LikeType;
import ru.practicum.explorewithme.repository.*;
import ru.practicum.explorewithme.dto.EventFullDto;
import ru.practicum.explorewithme.dto.EventShortDto;
import ru.practicum.explorewithme.dto.NewEventDto;
import ru.practicum.explorewithme.dto.UpdateEventRequest;
import ru.practicum.explorewithme.entity.enums.EventStatus;
import ru.practicum.explorewithme.mapper.EventMapper;
import ru.practicum.explorewithme.dto.ParticipationRequestDto;
import ru.practicum.explorewithme.entity.enums.RequestStatus;
import ru.practicum.explorewithme.mapper.RequestMapper;

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

    private final LikeRepository likeRepository;

    private final EventRepository eventsRepository;

    private final UserRepository usersRepository;

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


    private Event getEvent(Long eventId) throws AccessException {
        Event event = eventsRepository.findById(eventId).orElseThrow(
                () -> new AccessException("Event ID not found.")
        );

        return event;
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

    @Override
    @Transactional
    public void addLike(Long userId, Long eventId, LikeType likeType)
            throws AccessException {
        Event event = getEvent(eventId);
        if (!userRepository.existsById(userId)) {
            throw new AccessException("User not found.");
        }
        if (userId.equals(event.getInitiator().getId())) {
            throw new AccessException("Your event");
        }

        Optional<Like> like = likeRepository.findByEventIdAndUserId(userId, eventId);
        if (like.isPresent()) {
            if (like.get().getType() != likeType) {
                LikeType deleteType = LikeType.LIKE;
                if (likeType == LikeType.LIKE) {
                    deleteType = LikeType.DISLIKE;
                }
                removeLike(userId, eventId, deleteType);
            } else {
                throw new AccessException("Second Like!");
            }
        }
        likeRepository.saveAndFlush(new Like(null, userId, eventId, likeType));
        if (likeType == LikeType.LIKE) {
            eventsRepository.incrementRate(eventId);
        } else {
            eventsRepository.decrementRate(eventId);
        }

        User initiator = event.getInitiator();
        //User initiator = event.getInitiator();
        initiator.setRate(getRate(initiator.getId()));
        usersRepository.save(initiator);
    }


    @Override
    @Transactional
    public void removeLike(Long userId, Long eventId, LikeType likeType)
            throws AccessException {
        Event event = getEvent(eventId);
        if (!userRepository.existsById(userId)) {
            throw new AccessException("User not found.");
        }
        if (userId.equals(event.getInitiator().getId())) {
            throw new AccessException("Your event");
        }

        Like like = likeRepository.findByUserIdAndEventIdAndType(userId, eventId, likeType)
                .orElseThrow(
                        () -> new AccessException(likeType + " not found.")
                );
        likeRepository.delete(like);

        if (likeType == LikeType.LIKE) {
            eventsRepository.decrementRate(eventId);
        } else {
            eventsRepository.incrementRate(eventId);
        }

        User initiator = event.getInitiator();
        initiator.setRate(getRate(initiator.getId()));
        usersRepository.save(initiator);
    }

    private Float getRate(Long userId) {
        int count = eventsRepository.countByInitiatorId(userId);
        long rate = eventsRepository.sumRateByInitiatorId(userId);

        return count == 0 ? 0.0F : (1.0F * rate / count);
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

    @Override
    public EventFullDto getEvent(long userId, long eventId) {
        Optional<Event> foundEvent = eventRepository.findByIdAndInitiatorId(eventId, userId);
        return eventMapper.toEventFullDto(
                foundEvent
                        .orElseThrow(() -> new EntityNotFoundException("Unable to find Event id " + eventId))
        );
    }

}
