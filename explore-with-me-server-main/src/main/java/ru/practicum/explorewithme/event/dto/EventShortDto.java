package ru.practicum.explorewithme.event.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.explorewithme.category.CategoryDto;
import ru.practicum.explorewithme.user.dto.UserShortDto;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventShortDto {
    private String annotation;
    private CategoryDto category;
    private long confirmedRequests;
    private LocalDateTime eventDate;
    private long id;
    private UserShortDto initiator;
    private boolean paid;
    private String title;
    private long views;
}
