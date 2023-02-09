package ru.practicum.ewm_main.comment.model;

import lombok.*;
import ru.practicum.ewm_main.event.model.Event;
import ru.practicum.ewm_main.user.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "comments")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 1000, nullable = false)
    private String text;

    @ManyToOne
    private User user;

    @ManyToOne
    private Event event;

    @Column(name = "created_on", nullable = false)
    private LocalDateTime createdOn;

    @Enumerated(EnumType.STRING)
    private CommentState state;
}
