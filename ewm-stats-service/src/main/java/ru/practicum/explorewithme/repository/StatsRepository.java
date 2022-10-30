package ru.practicum.explorewithme.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.explorewithme.dto.EndpointHit;
import ru.practicum.explorewithme.dto.ViewsStats;

import java.time.LocalDateTime;
import java.util.List;

public interface StatsRepository extends JpaRepository<EndpointHit, Long> {

    @Query("select " +
            "new ru.practicum.explorewithme.dto.ViewsStats(e.app, e.uri, COUNT(distinct e.ip)) " +
            "from EndpointHit e " +
            "where e.timestamp between :start and :end " +
            "group by e.app, e.uri"
    )
    List<ViewsStats> findAllUnique(LocalDateTime start, LocalDateTime end);

    @Query("select " +
            "new ru.practicum.explorewithme.dto.ViewsStats(e.app, e.uri, COUNT(e.ip)) " +
            "from EndpointHit e " +
            "where e.timestamp between :start and :end " +
            "group by e.app, e.uri"
    )
    List<ViewsStats> findAll(LocalDateTime start, LocalDateTime end);

    @Query("select " +
            "new ru.practicum.explorewithme.dto.ViewsStats(e.app, e.uri, COUNT(distinct e.ip)) " +
            "from EndpointHit e " +
            "where e.uri like :uri " +
            "and e.timestamp between :start and :end " +
            "group by e.app, e.uri"
    )
    ViewsStats findUnique(LocalDateTime start, LocalDateTime end, String uri);

    @Query("select " +
            "new ru.practicum.explorewithme.dto.ViewsStats(e.app, e.uri, COUNT(e.ip)) " +
            "from EndpointHit e " +
            "where e.uri like :uri " +
            "and e.timestamp between :start and :end " +
            "group by e.app, e.uri"
    )
    ViewsStats find(LocalDateTime start, LocalDateTime end, String uri);
}
