package ru.practicum.explorewithme.requests;

import lombok.*;
import ru.practicum.explorewithme.event.model.Event;
import ru.practicum.explorewithme.user.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "participation_requests")
public class ParticipationRequest {
    @Column
    private LocalDateTime created;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "requester_id")
    private User requester;

    @Column
    private RequestStatus status;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParticipationRequest request = (ParticipationRequest) o;
        return id == request.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
