package ru.practicum.explorewithme.compilation;

import lombok.*;
import ru.practicum.explorewithme.event.model.Event;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@ToString
@EqualsAndHashCode
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
