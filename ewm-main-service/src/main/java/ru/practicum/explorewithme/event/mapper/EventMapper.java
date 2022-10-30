package ru.practicum.explorewithme.event.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.explorewithme.category.mapper.CategoryMapper;
import ru.practicum.explorewithme.category.model.Category;
import ru.practicum.explorewithme.event.EventStatus;
import ru.practicum.explorewithme.event.dto.*;
import ru.practicum.explorewithme.event.model.Event;
import ru.practicum.explorewithme.event.model.Location;
import ru.practicum.explorewithme.user.mapper.UserMapper;
import ru.practicum.explorewithme.user.model.User;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class EventMapper {

    private final CategoryMapper categoryMapper;

    private final UserMapper userMapper;

    public EventShortDto toEventShortDto(Event event) {
        return new EventShortDto(
                event.getAnnotation(),
                categoryMapper.toCategoryDto(event.getCategory()),
                event.getConfirmedRequests().size(),
                event.getEventDate(),
                event.getId(),
                userMapper.toUserShortDto(event.getInitiator()),
                event.isPaid(),
                event.getTitle(),
                event.getViews()
        );
    }

    public EventFullDto toEventFullDto(Event event) {
        return new EventFullDto(
                event.getAnnotation(),
                categoryMapper.toCategoryDto(event.getCategory()),
                event.getConfirmedRequests().size(),
                event.getCreatedOn(),
                event.getDescription(),
                event.getEventDate(),
                event.getId(),
                userMapper.toUserShortDto(event.getInitiator()),
                event.getLocation(),
                event.isPaid(),
                event.getParticipantLimit(),
                event.getPublishedOn(),
                event.isRequestModeration(),
                event.getState(),
                event.getTitle(),
                event.getViews()
        );
    }

    public Event toEventModel(NewEventDto newEventDto, Category category, User initiator, Location location) {
        return new Event(
                newEventDto.getAnnotation(),
                category,
                List.of(),
                LocalDateTime.now(),
                newEventDto.getDescription(),
                newEventDto.getEventDate(),
                0,
                initiator,
                location,
                newEventDto.isPaid(),
                newEventDto.getParticipantLimit(),
                null,
                newEventDto.isRequestModeration(),
                EventStatus.PENDING,
                newEventDto.getTitle(),
                0
        );
    }


}
