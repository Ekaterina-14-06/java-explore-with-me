package ru.practicum.explorewithme.event.model;

import lombok.*;
import ru.practicum.explorewithme.category.model.Category;
import ru.practicum.explorewithme.event.EventStatus;
import ru.practicum.explorewithme.requests.ParticipationRequest;
import ru.practicum.explorewithme.user.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "events")
public class Event {
    @Column(name = "annotation", columnDefinition = "text")
    private String annotation;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany
    @JoinColumn(name = "confirmed_request_id")
    private List<ParticipationRequest> confirmedRequests;

    @Column(name = "created_on")
    private LocalDateTime createdOn;

    @Column(name = "description", columnDefinition = "text")
    private String description;

    @Column(name = "event_date")
    private LocalDateTime eventDate;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "initiator_id")
    private User initiator;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;

    @Column
    private boolean paid;

    @Column(name = "participant_limit")
    private int participantLimit;

    @Column(name = "published_on")
    private LocalDateTime publishedOn;

    @Column(name = "request_moderation")
    private boolean requestModeration;

    @Column
    @Enumerated(EnumType.STRING)
    private EventStatus state;

    @Column
    private String title;

    @Column
    private long views;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return id == event.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
