package ru.practicum.explorewithme;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.explorewithme.EndpointHit;
import ru.practicum.explorewithme.ViewsStats;

import java.time.LocalDateTime;
import java.util.List;

public interface StatsRepository extends JpaRepository<EndpointHit, Long> {

    @Query("SELECT " +
            "new ru.practicum.explorewithme.dto.ViewsStats(e.app, e.uri, COUNT(distinct e.ip)) " +
            "FROM EndpointHit e " +
            "WHERE e.timestamp BETWEEN :start and :end " +
            "GROUP BY e.app, e.uri"
    )
    List<ViewsStats> findAllUnique(LocalDateTime start, LocalDateTime end);

    @Query("SELECT " +
            "new ru.practicum.explorewithme.dto.ViewsStats(e.app, e.uri, COUNT(e.ip)) " +
            "FROM EndpointHit e " +
            "WHERE e.timestamp BETWEEN :start and :end " +
            "GROUP BY e.app, e.uri"
    )
    List<ViewsStats> findAll(LocalDateTime start, LocalDateTime end);

    @Query("SELECT " +
            "new ru.practicum.explorewithme.dto.ViewsStats(e.app, e.uri, COUNT(distinct e.ip)) " +
            "FROM EndpointHit e " +
            "WHERE e.uri like :uri " +
            "and e.timestamp BETWEEN :start and :end " +
            "GROUP BY e.app, e.uri"
    )
    ViewsStats findUnique(LocalDateTime start, LocalDateTime end, String uri);

    @Query("SELECT " +
            "new ru.practicum.explorewithme.dto.ViewsStats(e.app, e.uri, COUNT(e.ip)) " +
            "FROM EndpointHit e " +
            "WHERE e.uri like :uri " +
            "and e.timestamp BETWEEN :start and :end " +
            "GROUP BY e.app, e.uri"
    )
    ViewsStats find(LocalDateTime start, LocalDateTime end, String uri);
}
