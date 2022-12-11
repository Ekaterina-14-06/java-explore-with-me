package ru.practicum.explorewithme.entity;


import lombok.*;
import ru.practicum.explorewithme.entity.enums.LikeType;

import javax.persistence.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@Entity
@Table(name = "likes")
@NoArgsConstructor
public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "event_id")
    private Long eventId;
    @Enumerated(EnumType.STRING)
    private LikeType type;
}
