package ru.practicum.explorewithme.controller;

import ru.practicum.explorewithme.dto.EventFullDto;
import ru.practicum.explorewithme.dto.EventShortDto;
import ru.practicum.explorewithme.entity.enums.EventSort;
import ru.practicum.explorewithme.service.PublicEventServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
@Validated
public class PublicEventController {

    private final PublicEventServiceImpl publicEventService;

    @GetMapping
    public List<EventShortDto> getEvents(
            HttpServletRequest request,
            @RequestParam(required = false) String text,
            @RequestParam(required = false) List<Long> categories,
            @RequestParam(required = false) Boolean paid,
            @RequestParam(required = false) @DateTimeFormat LocalDateTime rangeStart,
            @RequestParam(required = false) @DateTimeFormat LocalDateTime rangeEnd,
            @RequestParam(required = false, defaultValue = "true") boolean onlyAvailable,
            @RequestParam(required = false) String sortParam,
            @RequestParam(required = false, defaultValue = "0") int from,
            @RequestParam(required = false, defaultValue = "10") int size
    ) {
        EventSort sort;
        if (sortParam == null) {
            sort = EventSort.EVENT_DATE;
        } else {
            sort = EventSort.from(sortParam).orElseThrow(() -> new IllegalArgumentException(sortParam));
        }
        return publicEventService.getEvents(request, text, categories, paid, rangeStart, rangeEnd, onlyAvailable, sort, from, size);
    }

    @GetMapping("/{id}")
    public EventFullDto getEvent(
            HttpServletRequest request,
            @PathVariable int id
    ) {
        return publicEventService.getEvent(request, id);
    }
}
