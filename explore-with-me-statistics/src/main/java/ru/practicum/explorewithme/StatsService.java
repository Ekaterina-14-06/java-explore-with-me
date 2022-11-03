package ru.practicum.explorewithme;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StatsService {

    private final StatsRepository statsRepository;

    @PostMapping("/hit")
    @Transactional
    public void hit(@RequestBody EndpointHit endpointHit) {
        statsRepository.save(endpointHit);
    }

    @GetMapping("/stats")
    public List<ViewsStats> stats(
            @RequestParam @DateTimeFormat LocalDateTime start,
            @RequestParam @DateTimeFormat LocalDateTime end,
            @RequestParam (required = false) List<String> uris,
            @RequestParam (required = false, defaultValue = "false") boolean unique
            ) {

        if (uris == null && unique) {
            return statsRepository.findAllUnique(start, end);
        } else if (uris == null) {
            return statsRepository.findAll(start, end);
        } else if (unique) {
            List<ViewsStats> statModels = new ArrayList<>();
            for (String uri : uris) {
                ViewsStats stats = statsRepository.findUnique(start, end, uri);
                statModels.add(stats);
            }
            return statModels;
        } else {
            List<ViewsStats> statModels = new ArrayList<>();
            for (String uri : uris) {
                ViewsStats stats = statsRepository.find(start, end, uri);
                statModels.add(stats);
            }
            return statModels;
        }
    }
}
