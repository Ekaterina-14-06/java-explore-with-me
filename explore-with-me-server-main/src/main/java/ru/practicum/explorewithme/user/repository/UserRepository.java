package ru.practicum.explorewithme.user.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.explorewithme.user.model.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("from User u " +
            "where u.id in :users")
    List<User> findAll(List<Long> users, Pageable pageable);
}
