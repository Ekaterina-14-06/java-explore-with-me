package ru.practicum.explorewithme.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.explorewithme.entity.Location;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminUpdateEventRequest {
        private String annotation;
        private Long category;
        private String description;
        private LocalDateTime eventDate;
        private Location location;
        private Boolean paid;
        private Integer participantLimit;
        private Boolean requestModeration;
        private String title;
}
