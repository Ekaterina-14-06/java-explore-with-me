package ru.practicum.explorewithme.entity.enums;

import java.util.Optional;

public enum EventSort {
    EVENT_DATE,
    VIEWS;

    public static Optional<EventSort> from(String sortParam) {
        for (EventSort sort : values()) {
            if (sort.name().equalsIgnoreCase(sortParam)) {
                return Optional.of(sort);
            }
        }
        return Optional.empty();
    }
}
