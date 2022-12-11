package ru.practicum.explorewithme.entity;

import lombok.*;
import ru.practicum.explorewithme.entity.Event;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "compilations")
public class Compilation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToMany
    @ToString.Exclude
    @JoinColumn(name = "event_id")
    private List<Event> events;

    @Column
    private boolean pinned;

    @Column(unique = true)
    private String title;

}
