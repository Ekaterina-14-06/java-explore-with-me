package ru.practicum.explorewithme.event.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateEventRequest {
    private String annotation;
    private Long category;
    private String description;
    private LocalDateTime eventDate;
    private Long eventId;
    private Boolean paid;
    private Integer participantLimit;
    private String title;
}
