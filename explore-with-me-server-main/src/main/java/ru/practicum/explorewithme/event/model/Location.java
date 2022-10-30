package ru.practicum.explorewithme.event.model;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "locations")
@NoArgsConstructor
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private long lat;

    @Column
    private long lon;

}
