package ru.practicum.explorewithme.compilation.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.compilation.dto.CompilationDto;
import ru.practicum.explorewithme.compilation.dto.AddingCompilationDto;
import ru.practicum.explorewithme.compilation.service.AdminCompilationsService;


@RestController
@RequestMapping("/admin/compilations")
@RequiredArgsConstructor
public class AdminCompilationsController {

    private final AdminCompilationsService adminCompilationsService;

    @PostMapping
    public CompilationDto postCompilation(
            @RequestBody AddingCompilationDto addingCompilationDto
    ) {
        return adminCompilationsService.postCompilation(addingCompilationDto);
    }

    @DeleteMapping("/{compId}")
    public void deleteCompilation(
            @PathVariable long compId
    ) {
        adminCompilationsService.deleteCompilation(compId);
    }

    @DeleteMapping("/{compId}/events/{eventId}")
    public void deleteEventFromCompilation(
            @PathVariable long compId,
            @PathVariable long eventId
    ) {
        adminCompilationsService.deleteEventFromCompilation(compId, eventId);
    }

    @PatchMapping("/{compId}/events/{eventId}")
    public void addEventToCompilation(
            @PathVariable long compId,
            @PathVariable long eventId
    ) {
        adminCompilationsService.addEventToCompilation(compId, eventId);
    }

    @DeleteMapping("/{compId}/pin")
    public void removeCompilationPin(
            @PathVariable long compId
    ) {
        adminCompilationsService.removeCompilationPin(compId);
    }

    @PatchMapping("/{compId}/pin")
    public void addCompilationPin(
            @PathVariable long compId
    ) {
        adminCompilationsService.addCompilationPin(compId);
    }
}
