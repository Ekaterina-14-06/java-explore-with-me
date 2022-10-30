package ru.practicum.explorewithme.requests;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RequestRepository extends JpaRepository<ParticipationRequest, Long> {

    List<ParticipationRequest> findAllByEventId(long eventId);

    List<ParticipationRequest> findAllByRequesterId(long userId);

    Optional<ParticipationRequest> findByRequesterIdAndId(long userId, long eventId);
}
