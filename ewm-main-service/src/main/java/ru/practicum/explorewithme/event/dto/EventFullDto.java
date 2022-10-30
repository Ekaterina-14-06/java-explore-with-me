package ru.practicum.explorewithme.event.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.practicum.explorewithme.category.dto.CategoryDto;
import ru.practicum.explorewithme.event.EventStatus;
import ru.practicum.explorewithme.event.model.Location;
import ru.practicum.explorewithme.user.dto.UserShortDto;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class EventFullDto {
    private String annotation;
    private CategoryDto category;
    private long confirmedRequests;
    private LocalDateTime createdOn;
    private String description;
    private LocalDateTime eventDate;
    private long id;
    private UserShortDto initiator;
    private Location location;
    private boolean paid;
    private int participantLimit;
    private LocalDateTime publishedOn;
    private boolean requestModeration;
    private EventStatus state;
    private String title;
    private long views;
}
