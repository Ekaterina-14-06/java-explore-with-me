package ru.practicum.explorewithme.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.explorewithme.entity.Category;
import ru.practicum.explorewithme.dto.EventFullDto;
import ru.practicum.explorewithme.dto.EventShortDto;
import ru.practicum.explorewithme.dto.NewEventDto;
import ru.practicum.explorewithme.entity.enums.EventStatus;
import ru.practicum.explorewithme.entity.Event;
import ru.practicum.explorewithme.entity.Location;
import ru.practicum.explorewithme.entity.User;

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
