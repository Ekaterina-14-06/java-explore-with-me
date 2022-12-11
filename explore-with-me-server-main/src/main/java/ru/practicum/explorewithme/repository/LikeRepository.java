package ru.practicum.explorewithme.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.explorewithme.entity.Like;
import ru.practicum.explorewithme.entity.enums.LikeType;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {

    Optional<Like> findByEventIdAndUserId(Long userId, Long eventId);

    Optional<Like> findByUserIdAndEventIdAndType(Long userId, Long eventId, LikeType likeType);
}
