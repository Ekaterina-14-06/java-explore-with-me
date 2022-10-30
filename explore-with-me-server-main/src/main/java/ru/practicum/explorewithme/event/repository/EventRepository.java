package ru.practicum.explorewithme.event.repository;

import ru.practicum.explorewithme.event.EventStatus;
import ru.practicum.explorewithme.event.model.Event;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public interface EventRepository extends JpaRepository<Event, Long> {

    Optional<Event> findByIdAndState(long eventId, EventStatus state);

    @Query("from Event e " +
            "where (upper(e.annotation) like upper(concat('%', :text, '%')) " +
            "or upper(e.description) like upper(concat('%', :text, '%'))) " +
            "and e.category.id in :categories " +
            "and e.paid = :paid " +
            "and e.eventDate between :rangeStart and :rangeEnd " +
            "and e.state = 'PUBLISHED'"
    )
    List<Event> findAll(
            String text,
            List<Long> categories,
            boolean paid,
            LocalDateTime rangeStart,
            LocalDateTime rangeEnd,
            Pageable pageable
    );

    @Query("from Event e " +
            "where (upper(e.annotation) like upper(concat('%', :text, '%')) " +
            "or upper(e.description) like upper(concat('%', :text, '%'))) " +
            "and e.paid = :paid " +
            "and e.eventDate between :rangeStart and :rangeEnd " +
            "and e.state = 'PUBLISHED'"
    )
    List<Event> findAll(
            String text,
            boolean paid,
            LocalDateTime rangeStart,
            LocalDateTime rangeEnd,
            Pageable pageable
    );

    @Query("from Event e " +
            "where (upper(e.annotation) like upper(concat('%', :text, '%')) " +
            "or upper(e.description) like upper(concat('%', :text, '%'))) " +
            "and e.category.id in :categories " +
            "and e.eventDate between :rangeStart and :rangeEnd " +
            "and e.state = 'PUBLISHED'"
    )
    List<Event> findAll(
            String text,
            List<Long> categories,
            LocalDateTime rangeStart,
            LocalDateTime rangeEnd,
            Pageable pageable
    );

    @Query("from Event e " +
            "where (upper(e.annotation) like upper(concat('%', :text, '%')) " +
            "or upper(e.description) like upper(concat('%', :text, '%'))) " +
            "and e.eventDate between :rangeStart and :rangeEnd " +
            "and e.state = 'PUBLISHED'"
    )
    List<Event> findAll(
            String text,
            LocalDateTime rangeStart,
            LocalDateTime rangeEnd,
            Pageable pageable
    );

    @Query("from Event e " +
            "where (upper(e.annotation) like upper(concat('%', :text, '%')) " +
            "or upper(e.description) like upper(concat('%', :text, '%'))) " +
            "and e.category.id in :categories " +
            "and e.paid = :paid " +
            "and e.eventDate between :rangeStart and :rangeEnd " +
            "and e.participantLimit > 0 " +
            "and e.state = 'PUBLISHED'"
    )
    List<Event> findAllAvailable(
            String text,
            List<Long> categories,
            boolean paid,
            LocalDateTime rangeStart,
            LocalDateTime rangeEnd,
            Pageable pageable
    );

    @Query("from Event e " +
            "where (upper(e.annotation) like upper(concat('%', :text, '%')) " +
            "or upper(e.description) like upper(concat('%', :text, '%'))) " +
            "and e.paid = :paid " +
            "and e.eventDate between :rangeStart and :rangeEnd " +
            "and e.participantLimit > 0 " +
            "and e.state = 'PUBLISHED'"
    )
    List<Event> findAllAvailable(
            String text,
            boolean paid,
            LocalDateTime rangeStart,
            LocalDateTime rangeEnd,
            Pageable pageable
    );

    @Query("from Event e " +
            "where (upper(e.annotation) like upper(concat('%', :text, '%')) " +
            "or upper(e.description) like upper(concat('%', :text, '%'))) " +
            "and e.category.id in :categories " +
            "and e.eventDate between :rangeStart and :rangeEnd " +
            "and e.participantLimit > 0 " +
            "and e.state = 'PUBLISHED'"
    )
    List<Event> findAllAvailable(
            String text,
            List<Long> categories,
            LocalDateTime rangeStart,
            LocalDateTime rangeEnd,
            Pageable pageable
    );

    @Query("from Event e " +
            "where (upper(e.annotation) like upper(concat('%', :text, '%')) " +
            "or upper(e.description) like upper(concat('%', :text, '%'))) " +
            "and e.eventDate between :rangeStart and :rangeEnd " +
            "and e.participantLimit > 0 " +
            "and e.state = 'PUBLISHED'"
    )
    List<Event> findAllAvailable(
            String text,
            LocalDateTime rangeStart,
            LocalDateTime rangeEnd,
            Pageable pageable
    );

    @Query("from Event e " +
            "where e.initiator.id in :users " +
            "and e.state in :states " +
            "and e.category.id in :categories " +
            "and e.eventDate between :rangeStart and :rangeEnd")
    List<Event> findAllByInitiatorsAndStatesAndCategories(
            List<Long> users,
            List<EventStatus> states,
            List<Long> categories,
            LocalDateTime rangeStart,
            LocalDateTime rangeEnd,
            Pageable pageable
    );

    @Query("from Event e " +
            "where e.eventDate between :rangeStart and :rangeEnd")
    List<Event> findAll(
            LocalDateTime rangeStart,
            LocalDateTime rangeEnd,
            Pageable pageable
    );

    @Query("from Event e " +
            "where e.initiator.id in :users " +
            "and e.eventDate between :rangeStart and :rangeEnd")
    List<Event> findAllByInitiators(
            List<Long> users,
            LocalDateTime rangeStart,
            LocalDateTime rangeEnd,
            Pageable pageable
    );

    @Query("from Event e " +
            "where e.state in :states " +
            "and e.eventDate between :rangeStart and :rangeEnd")
    List<Event> findAllByStates(
            List<EventStatus> states,
            LocalDateTime rangeStart,
            LocalDateTime rangeEnd,
            Pageable pageable
    );

    @Query("from Event e " +
            "where e.category.id in :categories " +
            "and e.eventDate between :rangeStart and :rangeEnd")
    List<Event> findAllByCategories(
            List<Long> categories,
            LocalDateTime rangeStart,
            LocalDateTime rangeEnd,
            Pageable pageable
    );

    @Query("from Event e " +
            "where e.initiator.id in :users " +
            "and e.state in :states " +
            "and e.eventDate between :rangeStart and :rangeEnd")
    List<Event> findAllByInitiatorsAndStates(
            List<Long> users,
            List<EventStatus> states,
            LocalDateTime rangeStart,
            LocalDateTime rangeEnd,
            Pageable pageable
    );

    @Query("from Event e " +
            "where e.initiator.id in :users " +
            "and e.category.id in :categories " +
            "and e.eventDate between :rangeStart and :rangeEnd")
    List<Event> findAllByInitiatorsAndCategories(
            List<Long> users,
            List<Long> categories,
            LocalDateTime rangeStart,
            LocalDateTime rangeEnd,
            Pageable pageable
    );

    @Query("from Event e " +
            "where e.state in :states " +
            "and e.category.id in :categories " +
            "and e.eventDate between :rangeStart and :rangeEnd")
    List<Event> findAllByStatesAndCategories(
            List<EventStatus> states,
            List<Long> categories,
            LocalDateTime rangeStart,
            LocalDateTime rangeEnd,
            Pageable pageable
    );

    List<Event> findAllByInitiatorId(long userId, Pageable pageable);

    Optional<Event> findByIdAndInitiatorId(long eventId, long userId);
}
